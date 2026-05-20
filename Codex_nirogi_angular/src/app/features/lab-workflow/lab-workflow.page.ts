import { ScrollingModule } from '@angular/cdk/scrolling';
import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { catchError, finalize, of } from 'rxjs';

import { LabQueueItem, LabTestRecord } from '../../core/models/nirogi.models';
import { LabWorkflowService } from '../../core/services/lab-workflow.service';
import { PageHeaderComponent } from '../../shared/ui/page-header.component';
import { StatusChipComponent } from '../../shared/ui/status-chip.component';

@Component({
  selector: 'app-lab-workflow-page',
  standalone: true,
  imports: [ReactiveFormsModule, ScrollingModule, PageHeaderComponent, StatusChipComponent],
  templateUrl: './lab-workflow.page.html',
  styleUrl: './lab-workflow.page.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LabWorkflowPage {
  private readonly fb = inject(NonNullableFormBuilder);
  private readonly labService = inject(LabWorkflowService);

  readonly saving = signal(false);
  readonly loadingTests = signal(false);
  readonly message = signal('');
  readonly loadedTests = signal<LabTestRecord[]>([]);
  readonly queue = toSignal(this.labService.pendingQueue().pipe(catchError(() => of([] as LabQueueItem[]))), {
    initialValue: [],
  });

  readonly form = this.fb.group({
    referenceId: ['', Validators.required],
    results: this.fb.array([this.createResultGroup('')]),
  });

  constructor() {
    this.resultRows.clear();
  }

  get resultRows() {
    return this.form.controls.results;
  }

  loadTests(): void {
    const referenceId = this.form.controls.referenceId.value.trim();
    if (!referenceId) {
      return;
    }

    this.loadingTests.set(true);
    this.message.set('');
    this.loadedTests.set([]);
    this.resultRows.clear();

    this.labService
      .testsByReference(referenceId)
      .pipe(finalize(() => this.loadingTests.set(false)))
      .subscribe({
        next: (tests) => {
          this.loadedTests.set(tests);
          tests.forEach((test) => this.resultRows.push(this.createResultGroup(test.testCode, test.normalRange ?? '')));
          if (!tests.length) {
            this.message.set('No lab tests found for this reference ID.');
          }
        },
        error: (error) => this.message.set(error.error?.message ?? 'Unable to fetch lab tests for this reference ID.'),
      });
  }

  submit(): void {
    if (this.form.invalid || !this.resultRows.length) {
      this.form.markAllAsTouched();
      return;
    }

    const value = this.form.getRawValue();
    this.saving.set(true);
    this.message.set('');

    this.labService
      .submitResults({
        referenceId: value.referenceId,
        results: value.results,
      })
      .pipe(finalize(() => this.saving.set(false)))
      .subscribe({
        next: (response) => this.message.set(response.message || 'Lab results submitted.'),
        error: (error) => this.message.set(error.error?.message ?? 'Unable to submit lab results.'),
      });
  }

  trackReference(_: number, item: LabQueueItem): string {
    return item.referenceId;
  }

  private createResultGroup(testCode: string, normalRange = '') {
    return this.fb.group({
      testCode: [testCode, Validators.required],
      resultValue: ['', Validators.required],
      normalRange,
      remarks: '',
    });
  }
}
