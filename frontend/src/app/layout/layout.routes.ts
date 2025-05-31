import { Routes } from '@angular/router';
import { LayoutComponent } from './layout.component';
import { profileRoutes } from '../features/profile/profile.routes';
import { doctorRoutes } from '../features/doctor/doctor.routes';

export const layoutRoutes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: 'dashboard',
        loadComponent: () =>
          import('../features/dashboard/dashboard.component').then(
            (m) => m.DashboardComponent
          ),
      },
      ...profileRoutes,
      ...doctorRoutes,
      {
        path: '',
        redirectTo: '/dashboard',
        pathMatch: 'full',
      },
      {
        path: '**',
        redirectTo: 'dashboard',
      },
    ],
  },
];
