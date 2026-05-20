import { ScrollingModule } from '@angular/cdk/scrolling';
import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { NonNullableFormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, debounceTime, distinctUntilChanged, of, startWith, switchMap } from 'rxjs';

import { Beneficiary, PageResponse } from '../../core/models/nirogi.models';
import { BeneficiaryService } from '../../core/services/beneficiary.service';
import { MasterDataService } from '../../core/services/master-data.service';
import {
  ageDisplayFromDateOfBirth,
  categoryFromDateOfBirth,
  wasScreenedWithinTwoYears,
} from '../../core/utils/screening-category';
import { PageHeaderComponent } from '../../shared/ui/page-header.component';
import { StatusChipComponent } from '../../shared/ui/status-chip.component';

const EMPTY_PAGE: PageResponse<Beneficiary> = {
  content: [],
  totalElements: 0,
  totalPages: 0,
  page: 0,
  size: 50,
  hasNext: false,
};

@Component({
  selector: 'app-beneficiary-search-page',
  standalone: true,
  imports: [ReactiveFormsModule, ScrollingModule, PageHeaderComponent, StatusChipComponent],
  templateUrl: './beneficiary-search.page.html',
  styleUrl: './beneficiary-search.page.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BeneficiarySearchPage {
  private readonly fb = inject(NonNullableFormBuilder);
  private readonly beneficiaryService = inject(BeneficiaryService);
  private readonly masterDataService = inject(MasterDataService);
  private readonly router = inject(Router);

  readonly form = this.fb.group({
    pppId: '',
    districtId: this.fb.control<number | null>(null),
  });

  readonly masterData = toSignal(this.masterDataService.getAll().pipe(catchError(() => of(null))), {
    initialValue: null,
  });

  readonly results = toSignal(
    this.form.valueChanges.pipe(
      startWith(this.form.getRawValue()),
      debounceTime(320),
      distinctUntilChanged((previous, current) => JSON.stringify(previous) === JSON.stringify(current)),
      switchMap((value) =>
        this.beneficiaryService
          .search({
            pppId: value.pppId ?? '',
            districtId: value.districtId ?? null,
            page: 0,
            size: 50,
          })
          .pipe(catchError(() => of(EMPTY_PAGE))),
      ),
    ),
    { initialValue: EMPTY_PAGE },
  );

  trackMemberId(_: number, beneficiary: Beneficiary): string {
    return beneficiary.memberId;
  }

  categoryLabel(beneficiary: Beneficiary): string {
    return categoryFromDateOfBirth(beneficiary.dateOfBirth);
  }

  ageLabel(beneficiary: Beneficiary): string {
    return ageDisplayFromDateOfBirth(beneficiary.dateOfBirth);
  }

  recentlyScreened(beneficiary: Beneficiary): boolean {
    return wasScreenedWithinTwoYears(beneficiary.lastScreeningDate);
  }

  startScreening(beneficiary: Beneficiary): void {
    const fullName = `${beneficiary.firstName} ${beneficiary.lastName}`.trim();

    this.router.navigate(['/screening/new'], {
      queryParams: {
        memberId: beneficiary.memberId,
        beneficiaryName: fullName,
        dateOfBirth: beneficiary.dateOfBirth,
        category: categoryFromDateOfBirth(beneficiary.dateOfBirth),
      },
    });
  }
}
