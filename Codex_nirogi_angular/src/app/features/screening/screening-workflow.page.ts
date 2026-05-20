import {
  ChangeDetectionStrategy,
  Component,
  ComponentRef,
  inject,
  signal,
  Type,
  ViewChild,
  ViewContainerRef,
} from '@angular/core';
import { takeUntilDestroyed, toSignal } from '@angular/core/rxjs-interop';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { catchError, finalize, map, of, startWith, switchMap } from 'rxjs';

import {
  CategoryFormPayload,
  COMMON_LAB_TESTS,
  ScreeningCategory,
  ScreeningResponse,
} from '../../core/models/nirogi.models';
import { LabWorkflowService } from '../../core/services/lab-workflow.service';
import { MasterDataService } from '../../core/services/master-data.service';
import { ScreeningService } from '../../core/services/screening.service';
import { ageDisplayFromDateOfBirth, categoryFromDateOfBirth } from '../../core/utils/screening-category';
import { LifecycleTrackerComponent } from '../../shared/ui/lifecycle-tracker.component';
import { PageHeaderComponent } from '../../shared/ui/page-header.component';
import { StatusChipComponent } from '../../shared/ui/status-chip.component';
import { CategoryFormComponent } from './category-forms/category-form.contract';

const CATEGORY_LOADERS: Record<ScreeningCategory, () => Promise<Type<CategoryFormComponent>>> = {
  CAT1: () => import('./category-forms/cat1-form.component').then((m) => m.Cat1FormComponent),
  CAT2: () => import('./category-forms/cat2-form.component').then((m) => m.Cat2FormComponent),
  CAT3: () => import('./category-forms/cat3-form.component').then((m) => m.Cat3FormComponent),
  CAT4: () => import('./category-forms/cat4-form.component').then((m) => m.Cat4FormComponent),
  CAT5: () => import('./category-forms/cat5-form.component').then((m) => m.Cat5FormComponent),
  CAT6: () => import('./category-forms/cat6-form.component').then((m) => m.Cat6FormComponent),
};

@Component({
  selector: 'app-screening-workflow-page',
  standalone: true,
  imports: [ReactiveFormsModule, PageHeaderComponent, StatusChipComponent, LifecycleTrackerComponent],
  templateUrl: './screening-workflow.page.html',
  styleUrl: './screening-workflow.page.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ScreeningWorkflowPage {
  private readonly fb = inject(NonNullableFormBuilder);
  private readonly route = inject(ActivatedRoute);
  private readonly masterDataService = inject(MasterDataService);
  private readonly screeningService = inject(ScreeningService);
  private readonly labService = inject(LabWorkflowService);
  private host?: ViewContainerRef;
  private componentRef?: ComponentRef<CategoryFormComponent>;
  private payloadSubscription?: { unsubscribe(): void };

  readonly labTests = COMMON_LAB_TESTS;
  readonly saving = signal(false);
  readonly sectionSaving = signal(false);
  readonly errorMessage = signal('');
  readonly response = signal<ScreeningResponse | null>(null);
  readonly selectedTests = signal<Set<string>>(new Set());
  readonly categoryPayload = signal<CategoryFormPayload | null>(null);
  readonly beneficiaryName = this.route.snapshot.queryParamMap.get('beneficiaryName') ?? 'Selected beneficiary';
  readonly dateOfBirth = this.route.snapshot.queryParamMap.get('dateOfBirth') ?? '';
  readonly selectedCategory: ScreeningCategory = this.dateOfBirth
    ? categoryFromDateOfBirth(this.dateOfBirth)
    : ((this.route.snapshot.queryParamMap.get('category') as ScreeningCategory | null) ?? 'CAT1');
  readonly ageLabel = this.dateOfBirth ? ageDisplayFromDateOfBirth(this.dateOfBirth) : 'Not available';

  readonly form = this.fb.group({
    memberId: [this.route.snapshot.queryParamMap.get('memberId') ?? '', Validators.required],
    category: [this.selectedCategory, Validators.required],
    districtId: this.fb.control<number | null>(null, Validators.required),
    facilityId: this.fb.control<number | null>(null, Validators.required),
  });

  readonly masterData = toSignal(this.masterDataService.getAll().pipe(catchError(() => of(null))), {
    initialValue: null,
  });

  readonly facilities = toSignal(
    this.form.controls.districtId.valueChanges.pipe(
      startWith(this.form.controls.districtId.value),
      switchMap((districtId) => (districtId ? this.masterDataService.facilitiesByDistrict(districtId) : of([]))),
      catchError(() => of([])),
    ),
    { initialValue: [] },
  );

  @ViewChild('categoryHost', { read: ViewContainerRef })
  set categoryHost(host: ViewContainerRef) {
    this.host = host;
    this.loadCategory(this.form.controls.category.value);
  }

  constructor() {
    this.form.controls.category.valueChanges.pipe(takeUntilDestroyed()).subscribe((category) => {
      this.response.set(null);
      this.loadCategory(category);
    });
  }

  toggleTest(test: string): void {
    this.selectedTests.update((current) => {
      const next = new Set(current);
      next.has(test) ? next.delete(test) : next.add(test);
      return next;
    });
  }

  createScreening(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const value = this.form.getRawValue();
    if (!value.districtId || !value.facilityId) {
      return;
    }

    this.errorMessage.set('');
    this.saving.set(true);

    this.screeningService
      .create({
        memberId: value.memberId,
        category: value.category,
        districtId: value.districtId,
        facilityId: value.facilityId,
        labRequired: this.selectedTests().size > 0,
      })
      .pipe(
        switchMap((created) =>
          this.selectedTests().size
            ? this.labService
                .createOrder({ referenceId: created.referenceId, tests: [...this.selectedTests()] })
                .pipe(map(() => created))
            : of(created),
        ),
        finalize(() => this.saving.set(false)),
      )
      .subscribe({
        next: (created) => this.response.set(created),
        error: (error) => this.errorMessage.set(error.error?.message ?? 'Unable to create screening record.'),
      });
  }

  saveClinicalSections(): void {
    const created = this.response();
    const payload = this.categoryPayload();

    if (!created || !payload) {
      return;
    }

    this.sectionSaving.set(true);
    this.errorMessage.set('');

    this.screeningService
      .saveData(created.referenceId, payload)
      .pipe(finalize(() => this.sectionSaving.set(false)))
      .subscribe({
        next: () =>
          this.response.update((value) =>
            value ? { ...value, status: value.status === 'PENDING' ? 'PENDING_LAB' : value.status } : value,
          ),
        error: (error) => this.errorMessage.set(error.error?.message ?? 'Unable to save clinical sections.'),
      });
  }

  private async loadCategory(category: ScreeningCategory): Promise<void> {
    if (!this.host) {
      return;
    }

    const componentType = await CATEGORY_LOADERS[category]();
    if (this.form.controls.category.value !== category) {
      return;
    }

    this.payloadSubscription?.unsubscribe();
    this.componentRef?.destroy();
    this.host.clear();

    this.componentRef = this.host.createComponent(componentType);
    this.categoryPayload.set(this.componentRef.instance.snapshot());
    this.payloadSubscription = this.componentRef.instance.payloadChange.subscribe((payload) =>
      this.categoryPayload.set(payload),
    );
  }
}
