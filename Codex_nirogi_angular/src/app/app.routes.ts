import { Routes } from '@angular/router';
import { authGuard } from './core/auth/auth.guard';
import { roleChildGuard, roleGuard } from './core/auth/role.guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./features/auth/login.page').then((m) => m.LoginPage),
  },
  {
    path: '',
    canActivate: [authGuard],
    canActivateChild: [roleChildGuard],
    loadComponent: () => import('./features/layout/app-shell.component').then((m) => m.AppShellComponent),
    children: [
      {
        path: 'dashboard',
        loadComponent: () => import('./features/dashboard/dashboard.page').then((m) => m.DashboardPage),
        data: { roles: ['SUPERADMIN', 'ADMIN', 'DOCTOR', 'NODAL_OFFICER'] },
      },
      {
        path: 'beneficiaries',
        loadComponent: () =>
          import('./features/beneficiary/beneficiary-search.page').then((m) => m.BeneficiarySearchPage),
        data: { roles: ['SUPERADMIN', 'ADMIN', 'DOCTOR', 'NODAL_OFFICER'] },
      },
      {
        path: 'screening/new',
        canActivate: [roleGuard],
        loadComponent: () =>
          import('./features/screening/screening-workflow.page').then((m) => m.ScreeningWorkflowPage),
        data: { roles: ['SUPERADMIN', 'DOCTOR'] },
      },
      {
        path: 'screening-data',
        canActivate: [roleGuard],
        loadComponent: () =>
          import('./features/screening-data/screening-data-editor.page').then(
            (m) => m.ScreeningDataEditorPage,
          ),
        data: { roles: ['SUPERADMIN', 'DOCTOR'] },
      },
      {
        path: 'lab',
        loadComponent: () => import('./features/lab-workflow/lab-workflow.page').then((m) => m.LabWorkflowPage),
        data: { roles: ['SUPERADMIN', 'ADMIN', 'DOCTOR', 'NODAL_OFFICER'] },
      },
      {
        path: 'reports',
        loadComponent: () => import('./features/reports/reports.page').then((m) => m.ReportsPage),
        data: { roles: ['SUPERADMIN', 'ADMIN', 'DOCTOR', 'NODAL_OFFICER'] },
      },
      {
        path: 'users',
        canActivate: [roleGuard],
        loadComponent: () => import('./features/users/users.page').then((m) => m.UsersPage),
        data: { roles: ['SUPERADMIN', 'ADMIN'] },
      },
      {
        path: 'facilities',
        canActivate: [roleGuard],
        loadComponent: () => import('./features/facilities/facilities.page').then((m) => m.FacilitiesPage),
        data: { roles: ['SUPERADMIN', 'ADMIN'] },
      },
      {
        path: 'master-data',
        canActivate: [roleGuard],
        loadComponent: () => import('./features/master-data/master-data.page').then((m) => m.MasterDataPage),
        data: { roles: ['SUPERADMIN', 'ADMIN'] },
      },
      { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
    ],
  },
  { path: '**', redirectTo: 'dashboard' },
];
