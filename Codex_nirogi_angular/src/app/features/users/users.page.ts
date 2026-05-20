import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { catchError, finalize, of, startWith, switchMap } from 'rxjs';

import { USER_ROLES, UserResponse, UserRole } from '../../core/models/nirogi.models';
import { MasterDataService } from '../../core/services/master-data.service';
import { UsersService } from '../../core/services/users.service';
import { PageHeaderComponent } from '../../shared/ui/page-header.component';
import { StatusChipComponent } from '../../shared/ui/status-chip.component';

@Component({
  selector: 'app-users-page',
  standalone: true,
  imports: [ReactiveFormsModule, PageHeaderComponent, StatusChipComponent],
  templateUrl: './users.page.html',
  styleUrl: './users.page.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UsersPage {
  private readonly fb = inject(NonNullableFormBuilder);
  private readonly masterDataService = inject(MasterDataService);
  private readonly usersService = inject(UsersService);

  readonly roles = USER_ROLES;
  readonly saving = signal(false);
  readonly disabling = signal(false);
  readonly message = signal('');
  readonly created = signal<UserResponse | null>(null);

  readonly form = this.fb.group({
    role: ['DOCTOR' as UserRole, Validators.required],
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    mobile: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    designationId: this.fb.control<number | null>(null),
    districtId: this.fb.control<number | null>(null),
    facilityId: this.fb.control<number | null>(null),
    facilityTypeId: this.fb.control<number | null>(null),
  });

  readonly masterData = toSignal(this.masterDataService.getAll().pipe(catchError(() => of(null))), {
    initialValue: null,
  });

  readonly facilities = toSignal(
    this.form.controls.districtId.valueChanges.pipe(
      startWith(this.form.controls.districtId.value),
      switchMap((districtId) => (districtId ? this.masterDataService.facilitiesByDistrict(districtId) : of([]))),
      catchError(() => of([])),
    ),
    { initialValue: [] },
  );

  create(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.saving.set(true);
    this.message.set('');

    this.usersService
      .create(this.form.getRawValue())
      .pipe(finalize(() => this.saving.set(false)))
      .subscribe({
        next: (user) => this.created.set(user),
        error: (error) => this.message.set(error.error?.message ?? 'Unable to create user.'),
      });
  }

  disable(user: UserResponse): void {
    this.disabling.set(true);
    this.usersService
      .disable(user.id)
      .pipe(finalize(() => this.disabling.set(false)))
      .subscribe({
        next: () => {
          this.created.update((current) => (current ? { ...current, isActive: false } : current));
          this.message.set('User disabled.');
        },
        error: (error) => this.message.set(error.error?.message ?? 'Unable to disable user.'),
      });
  }
}
