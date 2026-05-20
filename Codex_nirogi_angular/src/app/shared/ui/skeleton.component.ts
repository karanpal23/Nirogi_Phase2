import { ChangeDetectionStrategy, Component, input } from '@angular/core';

@Component({
  selector: 'app-skeleton',
  standalone: true,
  templateUrl: './skeleton.component.html',
  styleUrl: './skeleton.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SkeletonComponent {
  readonly rows = input(3);
  readonly height = input(4);

  rowsArray(): number[] {
    return Array.from({ length: this.rows() }, (_, index) => index);
  }
}
