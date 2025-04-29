import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatDivider } from '@angular/material/divider';
@Component({
  selector: 'app-toolbar',
  imports: [
    CommonModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatMenuModule,
    MatDivider,
  ],
  templateUrl: './toolbar.component.html',
  styleUrl: './toolbar.component.scss',
  standalone: true,
})
export class ToolbarComponent {}
