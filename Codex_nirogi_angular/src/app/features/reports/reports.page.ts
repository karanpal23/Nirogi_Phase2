import { HttpEventType } from '@angular/common/http';
import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { NonNullableFormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { catchError, finalize, of, startWith, switchMap } from 'rxjs';

import {
  ReportFormat,
  ReportFilter,
  ReportKind,
  SCREENING_CATEGORIES,
  ScreeningStatus,
} from '../../core/models/nirogi.models';
import { MasterDataService } from '../../core/services/master-data.service';
import { ReportsService } from '../../core/services/reports.service';
import { saveBlob } from '../../core/utils/download';
import { PageHeaderComponent } from '../../shared/ui/page-header.component';

const STATUSES: ScreeningStatus[] = ['PENDING', 'PENDING_LAB', 'PARTIAL_COMPLETED', 'COMPLETED', 'REFERRED'];

@Component({
  selector: 'app-reports-page',
  standalone: true,
  imports: [ReactiveFormsModule, MatProgressBarModule, PageHeaderComponent],
  templateUrl: './reports.page.html',
  styleUrl: './reports.page.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ReportsPage {
  private readonly fb = inject(NonNullableFormBuilder);
  private readonly masterDataService = inject(MasterDataService);
  private readonly reportsService = inject(ReportsService);

  readonly categories = SCREENING_CATEGORIES;
  readonly statuses = STATUSES;
  readonly downloading = signal(false);
  readonly progress = signal(0);
  readonly message = signal('');

  readonly filterForm = this.fb.group({
    districtId: this.fb.control<number | null>(null),
    facilityId: this.fb.control<number | null>(null),
    category: this.fb.control<string | null>(null),
    status: this.fb.control<string | null>(null),
    fromDate: '',
    toDate: '',
    diagnosis: '',
  });

  readonly masterData = toSignal(this.masterDataService.getAll().pipe(catchError(() => of(null))), {
    initialValue: null,
  });

  readonly facilities = toSignal(
    this.filterForm.controls.districtId.valueChanges.pipe(
      startWith(this.filterForm.controls.districtId.value),
      switchMap((districtId) => (districtId ? this.masterDataService.facilitiesByDistrict(districtId) : of([]))),
      catchError(() => of([])),
    ),
    { initialValue: [] },
  );

  download(kind: ReportKind, format: ReportFormat): void {
    const filter = this.filterForm.getRawValue();
    const payload: ReportFilter = {
      districtId: filter.districtId,
      facilityId: filter.facilityId,
      category: filter.category as ReportFilter['category'],
      status: filter.status as ReportFilter['status'],
      fromDate: filter.fromDate || null,
      toDate: filter.toDate || null,
      diagnosis: filter.diagnosis || null,
    };

    this.downloading.set(true);
    this.progress.set(0);
    this.message.set('');

    this.reportsService
      .download(kind, format, payload)
      .pipe(finalize(() => this.downloading.set(false)))
      .subscribe({
        next: (event) => {
          if (event.type === HttpEventType.DownloadProgress && event.total) {
            this.progress.set(Math.round((event.loaded / event.total) * 100));
          }

          if (event.type === HttpEventType.Response && event.body) {
            this.progress.set(100);
            saveBlob(event.body, `${kind}-report.${format === 'excel' ? 'xlsx' : 'csv'}`);
            this.message.set('Report download completed.');
          }
        },
        error: (error) => this.message.set(error.error?.message ?? 'Unable to download report.'),
      });
  }
}
