import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { apiUrl } from '../http/api-url';
import { CreateUserRequest, UserResponse } from '../models/nirogi.models';

@Injectable({ providedIn: 'root' })
export class UsersService {
  private readonly http = inject(HttpClient);

  create(request: CreateUserRequest): Observable<UserResponse> {
    return this.http.post<UserResponse>(apiUrl('/users'), request);
  }

  disable(userId: number): Observable<void> {
    return this.http.patch<void>(apiUrl(`/users/${userId}/disable`), {});
  }
}
