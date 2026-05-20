import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, Observable, of, shareReplay } from 'rxjs';

import { apiUrl } from '../http/api-url';
import { DashboardSummary } from '../models/nirogi.models';

const EMPTY_SUMMARY: DashboardSummary = {
  totalScreenings: 0,
  pendingLabReports: 0,
  completedReports: 0,
  referrals: 0,
  categoryCounts: { CAT1: 0, CAT2: 0, CAT3: 0, CAT4: 0, CAT5: 0, CAT6: 0 },
  districtWiseScreenings: [],
  diseaseStats: [],
  doctorProductivity: [],
  generatedAt: new Date().toISOString(),
};

@Injectable({ providedIn: 'root' })
export class DashboardService {
  private readonly http = inject(HttpClient);

  readonly summary$ = this.http.get<DashboardSummary>(apiUrl('/dashboard/summary')).pipe(
    catchError(() => of(EMPTY_SUMMARY)),
    shareReplay({ bufferSize: 1, refCount: true }),
  );
}
