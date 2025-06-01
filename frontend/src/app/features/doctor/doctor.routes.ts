import { Routes } from '@angular/router';

export const doctorRoutes: Routes = [
  {
    path: 'doctors',
    children: [
      {
        path: 'new',
        loadComponent: () =>
          import('./create-doctor/create-doctor.component').then(
            (m) => m.CreateDoctorComponent
          ),
      },
      {
        path: '',
        loadComponent: () =>
          import('./doctors/doctors.component').then((m) => m.DoctorsComponent),
      },
    ],
  },
];
