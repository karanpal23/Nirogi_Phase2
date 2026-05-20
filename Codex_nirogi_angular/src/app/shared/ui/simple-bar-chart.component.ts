import { ChangeDetectionStrategy, Component, computed, input } from '@angular/core';

@Component({
  selector: 'app-simple-bar-chart',
  standalone: true,
  templateUrl: './simple-bar-chart.component.html',
  styleUrl: './simple-bar-chart.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SimpleBarChartComponent {
  readonly data = input<Array<{ label: string; value: number }>>([]);
  readonly ariaLabel = input('Aggregate chart');
  readonly normalizedData = computed(() => {
    const data = this.data();
    const max = Math.max(...data.map((item) => item.value), 1);
    return data.map((item) => ({ ...item, width: Math.max((item.value / max) * 100, item.value ? 6 : 0) }));
  });
}
