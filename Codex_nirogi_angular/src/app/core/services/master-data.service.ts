import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, shareReplay } from 'rxjs';

import { apiUrl } from '../http/api-url';
import { MasterDataItem, MasterDataResponse } from '../models/nirogi.models';

@Injectable({ providedIn: 'root' })
export class MasterDataService {
  private readonly http = inject(HttpClient);
  private masterData$?: Observable<MasterDataResponse>;
  private readonly facilityCache = new Map<number, Observable<MasterDataItem[]>>();

  getAll(): Observable<MasterDataResponse> {
    this.masterData$ ??= this.http
      .get<MasterDataResponse>(apiUrl('/master-data'))
      .pipe(shareReplay({ bufferSize: 1, refCount: true }));

    return this.masterData$;
  }

  facilitiesByDistrict(districtId: number): Observable<MasterDataItem[]> {
    const cached = this.facilityCache.get(districtId);
    if (cached) {
      return cached;
    }

    const request$ = this.http
      .get<MasterDataItem[]>(apiUrl(`/master-data/facilities/${districtId}`))
      .pipe(shareReplay({ bufferSize: 1, refCount: true }));

    this.facilityCache.set(districtId, request$);
    return request$;
  }

  createDistrict(name: string): Observable<MasterDataItem> {
    this.masterData$ = undefined;
    return this.http.post<MasterDataItem>(apiUrl('/master-data/district'), { name });
  }

  createFacilityType(name: string, code: string): Observable<MasterDataItem> {
    this.masterData$ = undefined;
    return this.http.post<MasterDataItem>(apiUrl('/master-data/facility-type'), { name, code });
  }

  createDesignation(name: string, description = ''): Observable<MasterDataItem> {
    this.masterData$ = undefined;
    return this.http.post<MasterDataItem>(apiUrl('/master-data/designation'), { name, description });
  }

  createFacility(payload: {
    name: string;
    districtId: number;
    facilityTypeId: number;
  }): Observable<MasterDataItem> {
    this.facilityCache.delete(payload.districtId);
    return this.http.post<MasterDataItem>(apiUrl('/master-data/facility'), payload);
  }
}
