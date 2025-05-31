import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { DoctorRequest } from '../models/doctor.model';
import { catchError, EMPTY } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DoctorService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = environment.apiUrl + '/api/doctors';

  getDoctors() {
    // Logic to fetch doctors from an API or database
    return [];
  }

  // Example method to add a doctor
  addDoctor(doctor: DoctorRequest) {
    return this.http.post(`${this.apiUrl}`, doctor).pipe(
      catchError((error) => {
        console.error('Error adding doctor:', error);
        return EMPTY;
      })
    );
  }
}
