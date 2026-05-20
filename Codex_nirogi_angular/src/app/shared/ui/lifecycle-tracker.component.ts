import { ChangeDetectionStrategy, Component, computed, input } from '@angular/core';

import { ScreeningStatus } from '../../core/models/nirogi.models';

const STATUS_ORDER: ScreeningStatus[] = ['PENDING', 'PENDING_LAB', 'PARTIAL_COMPLETED', 'COMPLETED'];

@Component({
  selector: 'app-lifecycle-tracker',
  standalone: true,
  templateUrl: './lifecycle-tracker.component.html',
  styleUrl: './lifecycle-tracker.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LifecycleTrackerComponent {
  readonly status = input<ScreeningStatus>('PENDING');
  readonly steps = computed(() => {
    const currentIndex = STATUS_ORDER.indexOf(this.status());

    return STATUS_ORDER.map((status, index) => ({
      status,
      label: status.replaceAll('_', ' '),
      active: index === currentIndex,
      complete: index < currentIndex || this.status() === 'REFERRED',
    }));
  });
}
