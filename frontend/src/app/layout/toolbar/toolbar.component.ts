import { CommonModule } from '@angular/common';
import { Component, inject, output } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatDivider } from '@angular/material/divider';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
@Component({
  selector: 'app-toolbar',
  imports: [
    CommonModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatMenuModule,
    MatDivider,
    RouterLink,
  ],
  templateUrl: './toolbar.component.html',
  styleUrl: './toolbar.component.scss',
  standalone: true,
})
export class ToolbarComponent {
  public sidebarToggle = output<void>();
  toggleSidebar() {
    this.sidebarToggle.emit();
  }
  private readonly authService = inject(AuthService);
  logout() {
    this.authService.logout();
  }
}
