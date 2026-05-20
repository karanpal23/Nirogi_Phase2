import { computed, Injectable, signal } from '@angular/core';

import { environment } from '../../../environments/environment';

export interface AppNotification {
  id: string;
  type: 'LAB_PENDING' | 'LAB_OVERDUE' | 'REFERRAL' | 'REPORT_COMPLETED' | 'SYSTEM';
  title: string;
  message: string;
  createdAt: string;
  read: boolean;
}

@Injectable({ providedIn: 'root' })
export class NotificationService {
  private socket?: WebSocket;

  readonly notifications = signal<AppNotification[]>([]);
  readonly unreadCount = computed(() => this.notifications().filter((item) => !item.read).length);

  connect(): void {
    if (!environment.wsBaseUrl || this.socket) {
      return;
    }

    this.socket = new WebSocket(environment.wsBaseUrl);
    this.socket.onmessage = (event) => {
      const payload = JSON.parse(event.data) as AppNotification;
      this.notifications.update((items) => [payload, ...items].slice(0, 50));
    };
    this.socket.onclose = () => {
      this.socket = undefined;
    };
  }

  markAllRead(): void {
    this.notifications.update((items) => items.map((item) => ({ ...item, read: true })));
  }
}
