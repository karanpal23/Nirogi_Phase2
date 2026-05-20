import { HttpClient } from '@angular/common/http';
import { computed, inject, Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, finalize, map, Observable, of, shareReplay, tap } from 'rxjs';

import { apiUrl } from '../http/api-url';
import { AuthUser, LoginRequest, UserRole } from '../models/nirogi.models';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);
  private sessionRequest$: Observable<AuthUser | null> | null = null;

  readonly currentUser = signal<AuthUser | null>(null);
  readonly loading = signal(false);
  readonly initialized = signal(false);
  readonly isAuthenticated = computed(() => this.currentUser() !== null);
  readonly displayName = computed(() => {
    const user = this.currentUser();
    return user ? `${user.firstName} ${user.lastName}`.trim() || user.username : '';
  });

  login(credentials: LoginRequest): Observable<AuthUser> {
    this.loading.set(true);

    return this.http.post<AuthUser>(apiUrl('/auth/login'), credentials).pipe(
      tap((user) => {
        this.currentUser.set(user);
        this.initialized.set(true);
      }),
      finalize(() => this.loading.set(false)),
    );
  }

  ensureSession(): Observable<AuthUser | null> {
    if (this.initialized()) {
      return of(this.currentUser());
    }

    if (!this.sessionRequest$) {
      this.sessionRequest$ = this.http.get<AuthUser>(apiUrl('/auth/me')).pipe(
        tap((user) => {
          this.currentUser.set(user);
          this.initialized.set(true);
        }),
        map((user) => user),
        catchError(() => {
          this.currentUser.set(null);
          this.initialized.set(true);
          return of(null);
        }),
        finalize(() => {
          this.sessionRequest$ = null;
        }),
        shareReplay({ bufferSize: 1, refCount: true }),
      );
    }

    return this.sessionRequest$;
  }

  logout(): void {
    this.http.post<void>(apiUrl('/auth/logout'), {}).pipe(catchError(() => of(undefined))).subscribe(() => {
      this.currentUser.set(null);
      this.initialized.set(true);
      this.router.navigateByUrl('/login', { replaceUrl: true });
    });
  }

  hasAnyRole(roles: UserRole[] | undefined): boolean {
    const user = this.currentUser();
    return !roles?.length || (user ? roles.includes(user.role) : false);
  }
}
