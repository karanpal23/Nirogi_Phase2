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
  selector: 'app-cat5-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './cat5-form.component.html',
  styleUrl: './cat5-form.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Cat5FormComponent {
  readonly definition = CATEGORY_FORM_DEFINITIONS.CAT5;
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
