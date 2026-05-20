import { inject } from '@angular/core';
import { CanActivateChildFn, CanActivateFn, Router } from '@angular/router';
import { map } from 'rxjs';

import { UserRole } from '../models/nirogi.models';
import { AuthService } from './auth.service';

const evaluateRole = (roles: UserRole[] | undefined) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  return auth.ensureSession().pipe(
    map((user) => {
      if (!user) {
        return router.parseUrl('/login');
      }

      return auth.hasAnyRole(roles) || router.parseUrl('/dashboard');
    }),
  );
};

export const roleGuard: CanActivateFn = (route) => evaluateRole(route.data['roles'] as UserRole[] | undefined);

export const roleChildGuard: CanActivateChildFn = (route) =>
  evaluateRole(route.data['roles'] as UserRole[] | undefined);
