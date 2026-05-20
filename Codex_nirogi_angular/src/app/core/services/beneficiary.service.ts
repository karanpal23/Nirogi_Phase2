import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable, of, shareReplay } from 'rxjs';

import { apiUrl } from '../http/api-url';
import {
  Beneficiary,
  BeneficiarySearchQuery,
  PageResponse,
} from '../models/nirogi.models';

@Injectable({ providedIn: 'root' })
export class BeneficiaryService {
  private readonly http = inject(HttpClient);
  private readonly searchCache = new Map<string, Observable<PageResponse<Beneficiary>>>();

 search(
    query: BeneficiarySearchQuery
  ): Observable<PageResponse<Beneficiary>> {

    if (!query.pppId.trim()) {
      return of({
        content: [],
        totalElements: 0,
        totalPages: 0,
        page: query.page,
        size: query.size,
        hasNext: false,
      });
    }

    const cacheKey = JSON.stringify(query);

    const cached = this.searchCache.get(cacheKey);

    if (cached) {
      return cached;
    }

    const requestBody = {
      pppId: query.pppId?.trim(),
      districtId: query.districtId,
      facilityId: query.facilityId,
      page: query.page,
      size: query.size,
    };

    const request$ = this.http
      .post<PageResponse<Beneficiary> | Beneficiary[]>(
        apiUrl('/beneficiary/search'),
        requestBody
      )
      .pipe(
        map((response) =>
          this.normalizeSearchResponse(
            response,
            query.page,
            query.size
          )
        )
      )
      .pipe(
        shareReplay({
          bufferSize: 1,
          refCount: true,
        })
      );

    this.searchCache.set(cacheKey, request$);

    return request$;
  }

  getFamily(pppId: string): Observable<Beneficiary[]> {
    return this.http.get<Beneficiary[]>(apiUrl(`/beneficiary/family/${encodeURIComponent(pppId)}`));
  }

  private normalizeSearchResponse(
    response: PageResponse<Beneficiary> | Beneficiary[],
    page: number,
    size: number,
  ): PageResponse<Beneficiary> {
    if (Array.isArray(response)) {
      return {
        content: response,
        totalElements: response.length,
        totalPages: response.length ? 1 : 0,
        page,
        size,
        hasNext: false,
      };
    }

    return response;
  }
}
