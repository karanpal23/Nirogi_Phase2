import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, map, Observable } from 'rxjs';

import { apiUrl } from '../http/api-url';
import {
  CreateLabOrderRequest,
  LabOrderResponse,
  LabQueueItem,
  LabTestRecord,
  SubmitLabResultsRequest,
} from '../models/nirogi.models';

type LabTestsApiResponse =
  | Array<string | Partial<LabTestRecord>>
  | {
      referenceId?: string;
      tests?: Array<string | Partial<LabTestRecord>>;
      labTests?: Array<string | Partial<LabTestRecord>>;
      results?: Array<string | Partial<LabTestRecord>>;
    };

@Injectable({ providedIn: 'root' })
export class LabWorkflowService {
  private readonly http = inject(HttpClient);

  createOrder(request: CreateLabOrderRequest): Observable<LabOrderResponse> {
    return this.http.post<LabOrderResponse>(apiUrl('/lab/order'), request);
  }

  submitResults(request: SubmitLabResultsRequest): Observable<LabOrderResponse> {
    return this.http.post<LabOrderResponse>(apiUrl('/lab/results'), request);
  }

  pendingQueue(): Observable<LabQueueItem[]> {
    return this.http.get<LabQueueItem[]>(apiUrl('/lab/pending'));
  }

  testsByReference(referenceId: string): Observable<LabTestRecord[]> {
    const encodedReference = encodeURIComponent(referenceId);

    return this.http.get<LabTestsApiResponse>(apiUrl(`/lab/tests/${encodedReference}`)).pipe(
      catchError(() => this.http.get<LabTestsApiResponse>(apiUrl(`/lab/order/${encodedReference}`))),
      map((response) => this.normalizeLabTests(response)),
    );
  }

  private normalizeLabTests(response: LabTestsApiResponse): LabTestRecord[] {
    const source = Array.isArray(response)
      ? response
      : (response.tests ?? response.labTests ?? response.results ?? []);

    return source
      .map((item) => {
        if (typeof item === 'string') {
          return { testCode: item };
        }

        return {
          testCode: item.testCode ?? '',
          testName: item.testName,
          normalRange: item.normalRange,
          status: item.status,
        };
      })
      .filter((item) => item.testCode.trim().length > 0);
  }
}
