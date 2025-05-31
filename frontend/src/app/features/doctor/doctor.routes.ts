import { Routes } from '@angular/router';

export const doctorRoutes: Routes = [
  {
    path: 'create-doctor',
    loadComponent: () =>
      import('./create-doctor/create-doctor.component').then(
        (m) => m.CreateDoctorComponent
      ),
  },
];
