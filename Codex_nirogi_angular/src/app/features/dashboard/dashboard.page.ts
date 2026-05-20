import { ChangeDetectionStrategy, Component, computed, inject } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';

import { DashboardService } from '../../core/services/dashboard.service';
import { MetricCardComponent } from '../../shared/ui/metric-card.component';
import { PageHeaderComponent } from '../../shared/ui/page-header.component';
import { SimpleBarChartComponent } from '../../shared/ui/simple-bar-chart.component';

@Component({
  selector: 'app-dashboard-page',
  standalone: true,
  imports: [PageHeaderComponent, MetricCardComponent, SimpleBarChartComponent],
  templateUrl: './dashboard.page.html',
  styleUrl: './dashboard.page.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DashboardPage {
  private readonly dashboard = inject(DashboardService);
  readonly summary = toSignal(this.dashboard.summary$, { initialValue: null });

  readonly categoryChart = computed(() => {
    const counts = this.summary()?.categoryCounts;
    return counts ? Object.entries(counts).map(([label, value]) => ({ label, value })) : [];
  });

  readonly districtChart = computed(() =>
    (this.summary()?.districtWiseScreenings ?? []).map((item) => ({ label: item.district, value: item.count })),
  );

  readonly diseaseChart = computed(() =>
    (this.summary()?.diseaseStats ?? []).map((item) => ({ label: item.diagnosis, value: item.count })),
  );
}
