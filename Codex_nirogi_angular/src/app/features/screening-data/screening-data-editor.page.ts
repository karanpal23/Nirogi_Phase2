import {
  ChangeDetectionStrategy,
  Component,
  ComponentRef,
  computed,
  inject,
  signal,
  Type,
  ViewChild,
  ViewContainerRef,
} from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { finalize } from 'rxjs';

import {
  CategoryFormPayload,
  SCREENING_CATEGORIES,
  ScreeningCategory,
  ScreeningDataView,
} from '../../core/models/nirogi.models';
import { ScreeningService } from '../../core/services/screening.service';
import { PageHeaderComponent } from '../../shared/ui/page-header.component';
import { StatusChipComponent } from '../../shared/ui/status-chip.component';
import { CategoryFormComponent } from '../screening/category-forms/category-form.contract';
import { screeningDataViewToPayload } from '../screening/category-forms/category-form-fields';

const CATEGORY_LOADERS: Record<ScreeningCategory, () => Promise<Type<CategoryFormComponent>>> = {
  CAT1: () => import('../screening/category-forms/cat1-form.component').then((m) => m.Cat1FormComponent),
  CAT2: () => import('../screening/category-forms/cat2-form.component').then((m) => m.Cat2FormComponent),
  CAT3: () => import('../screening/category-forms/cat3-form.component').then((m) => m.Cat3FormComponent),
  CAT4: () => import('../screening/category-forms/cat4-form.component').then((m) => m.Cat4FormComponent),
  CAT5: () => import('../screening/category-forms/cat5-form.component').then((m) => m.Cat5FormComponent),
  CAT6: () => import('../screening/category-forms/cat6-form.component').then((m) => m.Cat6FormComponent),
};

@Component({
  selector: 'app-screening-data-editor-page',
  standalone: true,
  imports: [ReactiveFormsModule, PageHeaderComponent, StatusChipComponent],
  templateUrl: './screening-data-editor.page.html',
  styleUrl: './screening-data-editor.page.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ScreeningDataEditorPage {
  private readonly fb = inject(NonNullableFormBuilder);
  private readonly screeningService = inject(ScreeningService);
  private host?: ViewContainerRef;
  private componentRef?: ComponentRef<CategoryFormComponent>;
  private payloadSubscription?: { unsubscribe(): void };

  readonly categories = SCREENING_CATEGORIES;
  readonly loading = signal(false);
  readonly saving = signal(false);
  readonly message = signal('');
  readonly existing = signal<ScreeningDataView | null>(null);
  readonly payload = signal<CategoryFormPayload | null>(null);
  readonly loadedPayload = signal<CategoryFormPayload | null>(null);
  readonly editing = signal(false);
  readonly sectionSummaries = computed(() => {
    const record = this.existing();

    if (!record) {
      return [];
    }

    return [
      { label: 'History', value: this.formatSection(record.historySection) },
      { label: 'General examination', value: this.formatSection(record.generalExamSection) },
      { label: 'Systemic examination', value: this.formatSection(record.systemicSection) },
      { label: 'Mandatory investigations', value: this.formatSection(record.mandatorySection) },
      { label: 'Diagnosis', value: this.formatSection(record.diagnosisSection) },
      { label: 'Prescription', value: this.formatSection(record.prescriptionSection) },
    ];
  });

  readonly lookupForm = this.fb.group({
    referenceId: ['', Validators.required],
    category: ['CAT1' as ScreeningCategory, Validators.required],
  });

  @ViewChild('categoryHost', { read: ViewContainerRef })
  set categoryHost(host: ViewContainerRef) {
    this.host = host;
    this.loadCategory(this.lookupForm.controls.category.value);
  }

  constructor() {
    this.lookupForm.controls.category.valueChanges.pipe(takeUntilDestroyed()).subscribe((category) => {
      if (this.editing()) {
        this.loadCategory(category);
      }
    });
  }

  loadExisting(): void {
    const referenceId = this.lookupForm.controls.referenceId.value.trim();
    if (!referenceId) {
      return;
    }

    this.loading.set(true);
    this.message.set('');

    this.screeningService
      .getData(referenceId)
      .pipe(finalize(() => this.loading.set(false)))
      .subscribe({
        next: (record) => {
          const payload = screeningDataViewToPayload(record);
          this.existing.set(record);
          this.loadedPayload.set(payload);
          this.payload.set(payload);
          this.editing.set(false);
          this.lookupForm.controls.category.setValue(record.category, { emitEvent: false });
        },
        error: (error) => this.message.set(error.error?.message ?? 'No screening data found for this reference.'),
      });
  }

  startEdit(): void {
    if (!this.existing()) {
      return;
    }

    this.editing.set(true);
    this.loadCategory(this.lookupForm.controls.category.value);
  }

  cancelEdit(): void {
    this.editing.set(false);
  }

  save(): void {
    const payload = this.payload();
    const referenceId = this.lookupForm.controls.referenceId.value.trim();

    if (!payload || !referenceId || !this.editing()) {
      return;
    }

    this.saving.set(true);
    this.message.set('');

    this.screeningService
      .saveData(referenceId, payload)
      .pipe(finalize(() => this.saving.set(false)))
      .subscribe({
        next: (result) => {
          this.loadedPayload.set(payload);
          this.message.set(result.message || `Saved ${result.referenceId}`);
        },
        error: (error) => this.message.set(error.error?.message ?? 'Unable to save screening data.'),
      });
  }

  patientName(record: ScreeningDataView): string {
    return (
      record.beneficiaryName ||
      record.patientName ||
      record.fullName ||
      `${record.firstName ?? ''} ${record.lastName ?? ''}`.trim() ||
      'Patient record'
    );
  }

  patientDetail(record: ScreeningDataView): string {
    const details = [
      record.memberId ? `Member ID ${record.memberId}` : '',
      record.pppId ? `PPP ${record.pppId}` : '',
      record.gender ?? '',
      record.age ? `${record.age} yrs` : '',
      record.dateOfBirth ? `DOB ${record.dateOfBirth}` : '',
    ].filter(Boolean);

    return details.join(' - ') || 'Reference-linked screening record';
  }

  locationDetail(record: ScreeningDataView): string {
    return [record.districtName, record.facilityName].filter(Boolean).join(' - ') || 'Facility details not returned';
  }

  private async loadCategory(category: ScreeningCategory): Promise<void> {
    if (!this.host) {
      return;
    }

    const componentType = await CATEGORY_LOADERS[category]();
    if (this.lookupForm.controls.category.value !== category) {
      return;
    }

    this.payloadSubscription?.unsubscribe();
    this.componentRef?.destroy();
    this.host.clear();

    this.componentRef = this.host.createComponent(componentType);
    this.payloadSubscription = this.componentRef.instance.payloadChange.subscribe((payload) => this.payload.set(payload));

    const loadedPayload = this.loadedPayload();
    if (loadedPayload) {
      this.componentRef.instance.patchPayload(loadedPayload);
    } else {
      this.payload.set(this.componentRef.instance.snapshot());
    }
  }

  private formatSection(value: string | undefined): string {
    if (!value) {
      return 'No saved values';
    }

    try {
      const parsed = JSON.parse(value) as unknown;
      return JSON.stringify(parsed, null, 2);
    } catch {
      return value;
    }
  }
}
