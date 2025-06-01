import { CommonModule } from '@angular/common';
import { Component, inject, signal, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Router, RouterModule } from '@angular/router';
import { UserProfile } from '../models/user-profile.model';
import { ProfileService } from '../services/profile.service';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-profile',
  imports: [
    CommonModule,
    RouterModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss',
})
export class ProfileComponent implements OnInit {
  private profileService = inject(ProfileService);
  private authService = inject(AuthService);
  private snackBar = inject(MatSnackBar);
  private router = inject(Router);

  profile = signal<UserProfile | null>(null);
  loading = signal<boolean>(true);

  ngOnInit(): void {
    this.loadProfile();
  }

  loadProfile(): void {
    const userId = this.authService.user.id;

    this.profileService.getUserProfile(userId).subscribe({
      next: (data) => {
        this.profile.set(data);
        this.loading.set(false);
      },
      error: (error) => {
        this.loading.set(false);

        if (error.status === 404) {
          this.snackBar.open('Please complete your profile', 'Close', {
            duration: 5000,
            horizontalPosition: 'center',
            verticalPosition: 'bottom',
          });

          this.router.navigate(['edit-profile']);
        }
      },
    });
  }
}
