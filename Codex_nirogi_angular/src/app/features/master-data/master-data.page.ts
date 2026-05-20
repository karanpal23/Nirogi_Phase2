import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { catchError, finalize, of } from 'rxjs';

import { MasterDataService } from '../../core/services/master-data.service';
import { PageHeaderComponent } from '../../shared/ui/page-header.component';

@Component({
  selector: 'app-master-data-page',
  standalone: true,
  imports: [ReactiveFormsModule, PageHeaderComponent],
  templateUrl: './master-data.page.html',
  styleUrl: './master-data.page.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class MasterDataPage {
  private readonly fb = inject(NonNullableFormBuilder);
  private readonly masterDataService = inject(MasterDataService);

  readonly saving = signal(false);
  readonly message = signal('');

  readonly districtForm = this.fb.group({ name: ['', Validators.required] });
  readonly facilityTypeForm = this.fb.group({
    name: ['', Validators.required],
    code: ['', Validators.required],
  });
  readonly designationForm = this.fb.group({
    name: ['', Validators.required],
    description: '',
  });

  readonly masterData = toSignal(this.masterDataService.getAll().pipe(catchError(() => of(null))), {
    initialValue: null,
  });

  createDistrict(): void {
    this.runCreate(() => this.masterDataService.createDistrict(this.districtForm.controls.name.value), 'District created.');
  }

  createFacilityType(): void {
    this.runCreate(
      () =>
        this.masterDataService.createFacilityType(
          this.facilityTypeForm.controls.name.value,
          this.facilityTypeForm.controls.code.value,
        ),
      'Facility type created.',
    );
  }

  createDesignation(): void {
    this.runCreate(
      () =>
        this.masterDataService.createDesignation(
          this.designationForm.controls.name.value,
          this.designationForm.controls.description.value,
        ),
      'Designation created.',
    );
  }

  join(items: Array<{ name: string }> | undefined): string {
    return items?.map((item) => item.name).join(', ') || 'No records loaded';
  }

  private runCreate(requestFactory: () => ReturnType<MasterDataService['createDistrict']>, success: string): void {
    this.saving.set(true);
    this.message.set('');
    requestFactory()
      .pipe(finalize(() => this.saving.set(false)))
      .subscribe({
        next: () => this.message.set(success),
        error: (error) => this.message.set(error.error?.message ?? 'Unable to save master data.'),
      });
  }
}
