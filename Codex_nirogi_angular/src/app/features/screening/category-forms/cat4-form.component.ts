import { ChangeDetectionStrategy, Component, output } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { ReactiveFormsModule } from '@angular/forms';
import { startWith } from 'rxjs';

import { CategoryFormPayload } from '../../../core/models/nirogi.models';
import {
  buildCategoryForm,
  CATEGORY_FORM_DEFINITIONS,
  isLongTextField,
  patchCategoryForm,
  snapshotCategoryForm,
} from './category-form-fields';

@Component({
  selector: 'app-cat4-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './cat4-form.component.html',
  styleUrl: './cat4-form.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Cat4FormComponent {
  readonly definition = CATEGORY_FORM_DEFINITIONS.CAT4;
  readonly isLongTextField = isLongTextField;
  readonly payloadChange = output<CategoryFormPayload>();
  readonly form = buildCategoryForm(this.definition);

  constructor() {
    this.form.valueChanges
      .pipe(startWith(this.form.getRawValue()), takeUntilDestroyed())
      .subscribe(() => this.payloadChange.emit(this.snapshot()));
  }

  snapshot(): CategoryFormPayload {
    return snapshotCategoryForm(this.definition, this.form);
  }

  patchPayload(payload: CategoryFormPayload): void {
    patchCategoryForm(this.definition, this.form, payload);
    this.payloadChange.emit(this.snapshot());
  }
}
