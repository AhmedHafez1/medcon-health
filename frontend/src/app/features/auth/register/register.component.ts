import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormBuilder,
  Validators,
  FormGroup,
} from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatRadioModule } from '@angular/material/radio';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { AuthService } from '../../../core/services/auth.service';
import { ROLE } from '../../../shared/enums/role.enum';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    MatCardModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatRadioModule,
    MatCheckboxModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatDividerModule,
    MatIconModule,
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss', '../auth.scss'],
})
export class RegisterComponent implements OnInit {
  roles = ['PATIENT', 'DOCTOR'];
  form!: FormGroup;
  hidePassword = true;
  isSubmitting = false;

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      role: [ROLE.PATIENT, Validators.required],
    });
  }

  onSubmit() {
    if (this.form.invalid) return;

    this.isSubmitting = true;

    this.auth.register(this.form.value).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.snackBar.open('Registration successful! Please log in.', 'Close', {
          duration: 5000,
          panelClass: 'success-snackbar',
        });
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.isSubmitting = false;
        this.snackBar.open(
          err?.message || 'Registration failed. Please try again.',
          'Close',
          {
            duration: 5000,
            panelClass: 'error-snackbar',
          }
        );
      },
    });
  }
}
