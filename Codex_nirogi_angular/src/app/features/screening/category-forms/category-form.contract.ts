import { OutputEmitterRef } from '@angular/core';

import { CategoryFormPayload } from '../../../core/models/nirogi.models';

export interface CategoryFormComponent {
  payloadChange: OutputEmitterRef<CategoryFormPayload>;
  snapshot(): CategoryFormPayload;
  patchPayload(payload: CategoryFormPayload): void;
}
