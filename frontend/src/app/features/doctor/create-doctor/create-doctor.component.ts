import { Component, inject, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormArray,
  ReactiveFormsModule,
} from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DoctorRequest } from '../models/doctor.model';
import { DoctorService } from '../services/doctor.service';
import { MatCardModule } from '@angular/material/card';
import { MatInput } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatOption, MatSelect } from '@angular/material/select';
import { MatIcon } from '@angular/material/icon';
import { CommonModule, TitleCasePipe } from '@angular/common';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { specializations } from '../models/specialization.enum';
import { DAYS } from '../../../shared/models/day.enum';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-create-doctor',
  imports: [
    MatCardModule,
    MatInput,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatSelect,
    MatOption,
    MatIcon,
    TitleCasePipe,
    CommonModule,
    MatSlideToggleModule,
    MatButtonModule,
  ],
  templateUrl: './create-doctor.component.html',
  styleUrl: './create-doctor.component.scss',
})
export class CreateDoctorComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly snackBar = inject(MatSnackBar);
  private readonly doctorService = inject(DoctorService);

  public doctorForm!: FormGroup;
  public specializations = specializations;
  public daysOfWeek = DAYS;

  ngOnInit(): void {
    this.doctorForm = this.createForm();
    this.addAvailability();
  }

  private createForm(): FormGroup {
    return this.fb.group({
      userId: [null, [Validators.required, Validators.min(1)]],
      specialization: ['', [Validators.required, Validators.maxLength(100)]],
      licenseNumber: ['', [Validators.required, Validators.maxLength(50)]],
      experience: [
        null,
        [Validators.required, Validators.min(0), Validators.max(50)],
      ],
      bio: ['', [Validators.maxLength(1000)]],
      consultationFee: [
        null,
        [Validators.required, Validators.min(0), Validators.max(10000)],
      ],
      availabilities: this.fb.array([]),
    });
  }

  get availabilities(): FormArray {
    return this.doctorForm.get('availabilities') as FormArray;
  }

  createAvailabilityGroup(): FormGroup {
    return this.fb.group({
      dayOfWeek: ['', Validators.required],
      startTime: ['', Validators.required],
      endTime: ['', Validators.required],
      isActive: [true],
    });
  }

  addAvailability(): void {
    this.availabilities.push(this.createAvailabilityGroup());
  }

  removeAvailability(index: number): void {
    if (this.availabilities.length > 1) {
      this.availabilities.removeAt(index);
    } else {
      this.snackBar.open(
        'At least one availability slot is required',
        'Close',
        {
          duration: 3000,
        }
      );
    }
  }

  getErrorMessage(fieldName: string): string {
    const field = this.doctorForm.get(fieldName);
    if (field?.hasError('required')) {
      return `${this.getFieldLabel(fieldName)} is required`;
    }
    if (field?.hasError('maxlength')) {
      const maxLength = field.errors?.['maxlength'].requiredLength;
      return `${this.getFieldLabel(
        fieldName
      )} must not exceed ${maxLength} characters`;
    }
    if (field?.hasError('min')) {
      const minValue = field.errors?.['min'].min;
      return `${this.getFieldLabel(fieldName)} cannot be less than ${minValue}`;
    }
    if (field?.hasError('max')) {
      const maxValue = field.errors?.['max'].max;
      return `${this.getFieldLabel(fieldName)} cannot exceed ${maxValue}`;
    }
    return '';
  }

  private getFieldLabel(fieldName: string): string {
    const labels: Record<string, string> = {
      userId: 'User ID',
      specialization: 'Specialization',
      licenseNumber: 'License Number',
      experience: 'Experience',
      bio: 'Bio',
      consultationFee: 'Consultation Fee',
    };
    return labels[fieldName] || fieldName;
  }

  onSubmit(): void {
    if (this.doctorForm.valid) {
      const doctorData: DoctorRequest = this.doctorForm.value;
      console.log('Doctor data to submit:', doctorData);

      this.doctorService.addDoctor(doctorData).subscribe({
        next: () => {
          this.snackBar.open('Doctor created successfully!', 'Close', {
            duration: 3000,
            panelClass: ['success-snackbar'],
          });
        },
        error: (error) => {
          console.error('Error creating doctor:', error);
          this.snackBar.open(
            'Failed to create doctor. Please try again.',
            'Close',
            {
              duration: 3000,
              panelClass: ['error-snackbar'],
            }
          );
        },
      });
    } else {
      this.markFormGroupTouched(this.doctorForm);
      this.snackBar.open('Please complete the form', 'Close', {
        duration: 3000,
        panelClass: ['error-snackbar'],
      });
    }
  }

  private markFormGroupTouched(formGroup: FormGroup | FormArray): void {
    Object.keys(formGroup.controls).forEach((field) => {
      const control = formGroup.get(field);
      if (control instanceof FormGroup || control instanceof FormArray) {
        this.markFormGroupTouched(control);
      } else {
        control?.markAsTouched({ onlySelf: true });
      }
    });
  }

  onReset(): void {
    this.doctorForm.reset();
    this.availabilities.clear();
    this.addAvailability();
  }
}
