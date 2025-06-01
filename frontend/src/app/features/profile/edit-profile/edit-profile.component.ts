import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { UserProfile } from '../models/user-profile.model';
import { ProfileService } from '../services/profile.service';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-edit-profile',
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    MatSnackBarModule,
  ],
  templateUrl: './edit-profile.component.html',
  styleUrl: './edit-profile.component.scss',
})
export class EditProfileComponent implements OnInit {
  private fb = inject(FormBuilder);
  private profileService = inject(ProfileService);
  private snackBar = inject(MatSnackBar);
  private router = inject(Router);
  private authService = inject(AuthService);

  // State signals
  profile = signal<UserProfile | null>(null);
  loading = signal<boolean>(true);
  error = signal<string | null>(null);
  submitting = signal<boolean>(false);
  imagePreview = signal<string | null>(null);

  profileForm!: FormGroup;
  selectedFile: File | null = null;

  ngOnInit(): void {
    this.initForm();
    this.loadProfile();
  }

  initForm(): void {
    this.profileForm = this.fb.group({
      email: [
        this.authService.user.email,
        [Validators.required, Validators.email],
      ],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      phone: [''],
      dob: [null],
      gender: [''],
      address: [''],
    });

    this.profileForm.get('email')?.disable();
  }

  loadProfile(): void {
    this.loading.set(true);
    this.error.set(null);

    this.profileService.getUserProfile(this.authService.user.id).subscribe({
      next: (data) => {
        this.profile.set(data);
        this.populateForm(data);
        this.loading.set(false);
      },
      error: (err) => {
        this.loading.set(false);

        if (err.status === 404) {
          return;
        }

        this.error.set(err.message || 'Failed to load profile');
      },
    });
  }

  populateForm(profile: UserProfile): void {
    // If the dateOfBirth is a string, convert it to a Date object
    const dob = profile.dob ? new Date(profile.dob) : null;

    this.profileForm.patchValue({
      email: profile.email,
      firstName: profile.firstName,
      lastName: profile.lastName,
      phone: profile.phone,
      dob: dob,
      gender: profile.gender,
      address: profile.address,
    });
  }

  onFileSelected(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    if (inputElement.files && inputElement.files.length > 0) {
      this.selectedFile = inputElement.files[0];

      // Create a preview
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview.set(reader.result as string);
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  removeImage(): void {
    this.selectedFile = null;
    this.imagePreview.set(null);
  }

  onSubmit(): void {
    if (this.profileForm.invalid) {
      return;
    }

    this.submitting.set(true);

    // First, handle image upload if there's a new image
    const handleProfileUpdate = () => {
      this.profileService
        .updateUserProfile({
          ...this.profileForm.value,
          email: this.profileForm.get('email')?.value,
        })
        .subscribe({
          next: () => {
            this.submitting.set(false);
            this.snackBar.open('Profile updated successfully', 'Close', {
              duration: 3000,
              horizontalPosition: 'center',
              verticalPosition: 'bottom',
            });
            this.router.navigate(['/profile']);
          },
          error: (err) => {
            this.submitting.set(false);
            this.snackBar.open(
              err.message || 'Failed to update profile',
              'Close',
              {
                duration: 5000,
                horizontalPosition: 'center',
                verticalPosition: 'bottom',
              }
            );
          },
        });
    };

    // If there's a file to upload, do that first
    if (this.selectedFile) {
      this.profileService.uploadProfileImage(this.selectedFile).subscribe({
        next: (response) => {
          // Update the profile image URL in our state
          if (this.profile()) {
            this.profile.update((profile) => ({
              ...profile!,
              profileImageUrl: response.profileImageUrl,
            }));
          }

          // Now proceed with updating the rest of the profile
          handleProfileUpdate();
        },
        error: (err) => {
          this.submitting.set(false);
          this.snackBar.open(err.message || 'Failed to upload image', 'Close', {
            duration: 5000,
            horizontalPosition: 'center',
            verticalPosition: 'bottom',
          });
        },
      });
    } else {
      // No image to upload, just update the profile
      handleProfileUpdate();
    }
  }
}
