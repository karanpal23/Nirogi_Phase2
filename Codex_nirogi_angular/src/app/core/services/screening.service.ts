import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { apiUrl } from '../http/api-url';
import {
  CategoryFormPayload,
  CreateScreeningRequest,
  SaveScreeningDataRequest,
  ScreeningDataView,
  ScreeningResponse,
} from '../models/nirogi.models';

@Injectable({ providedIn: 'root' })
export class ScreeningService {
  private readonly http = inject(HttpClient);

  create(request: CreateScreeningRequest): Observable<ScreeningResponse> {
    return this.http.post<ScreeningResponse>(apiUrl('/screening/create'), request);
  }

  saveData(referenceId: string, payload: CategoryFormPayload): Observable<{ referenceId: string; message: string }> {
    const request: SaveScreeningDataRequest = {
      referenceId,
      historySection: JSON.stringify(payload.historySection),
      generalExamSection: JSON.stringify(payload.generalExamSection),
      systemicSection: JSON.stringify(payload.systemicSection),
      mandatorySection: JSON.stringify(payload.mandatorySection),
      diagnosisSection: JSON.stringify(payload.diagnosisSection),
      prescriptionSection: JSON.stringify(payload.prescriptionSection),
    };

    return this.http.post<{ referenceId: string; message: string }>(apiUrl('/screening-data/save'), request);
  }

  getData(referenceId: string): Observable<ScreeningDataView> {
    return this.http.get<ScreeningDataView>(apiUrl(`/screening-data/${encodeURIComponent(referenceId)}`));
  }
}
