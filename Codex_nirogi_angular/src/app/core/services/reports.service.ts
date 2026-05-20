import { HttpClient, HttpEvent } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { apiUrl } from '../http/api-url';
import { ReportFilter, ReportFormat, ReportKind } from '../models/nirogi.models';

@Injectable({ providedIn: 'root' })
export class ReportsService {
  private readonly http = inject(HttpClient);

  download(kind: ReportKind, format: ReportFormat, filter: ReportFilter): Observable<HttpEvent<Blob>> {
    return this.http.post(apiUrl(`/reports/${kind}/${format}`), filter, {
      observe: 'events',
      reportProgress: true,
      responseType: 'blob',
    });
  }
}
