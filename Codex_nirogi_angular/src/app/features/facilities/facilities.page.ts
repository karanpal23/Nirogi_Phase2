import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { catchError, finalize, of } from 'rxjs';

import { MasterDataItem } from '../../core/models/nirogi.models';
import { MasterDataService } from '../../core/services/master-data.service';
import { PageHeaderComponent } from '../../shared/ui/page-header.component';

@Component({
  selector: 'app-facilities-page',
  standalone: true,
  imports: [ReactiveFormsModule, PageHeaderComponent],
  templateUrl: './facilities.page.html',
  styleUrl: './facilities.page.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class FacilitiesPage {
  private readonly fb = inject(NonNullableFormBuilder);
  private readonly masterDataService = inject(MasterDataService);

  readonly saving = signal(false);
  readonly message = signal('');
  readonly createdFacilities = signal<MasterDataItem[]>([]);

  readonly form = this.fb.group({
    name: ['', Validators.required],
    districtId: this.fb.control<number | null>(null, Validators.required),
    facilityTypeId: this.fb.control<number | null>(null, Validators.required),
  });

  readonly masterData = toSignal(this.masterDataService.getAll().pipe(catchError(() => of(null))), {
    initialValue: null,
  });

  create(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const value = this.form.getRawValue();
    if (!value.districtId || !value.facilityTypeId) {
      return;
    }

    this.saving.set(true);
    this.message.set('');

    this.masterDataService
      .createFacility({
        name: value.name,
        districtId: value.districtId,
        facilityTypeId: value.facilityTypeId,
      })
      .pipe(finalize(() => this.saving.set(false)))
      .subscribe({
        next: (facility) => {
          this.createdFacilities.update((items) => [facility, ...items].slice(0, 10));
          this.message.set('Facility created.');
          this.form.controls.name.reset('');
        },
        error: (error) => this.message.set(error.error?.message ?? 'Unable to create facility.'),
      });
  }
}
