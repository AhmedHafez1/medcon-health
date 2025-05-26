import { Routes } from '@angular/router';

export const profileRoutes: Routes = [
  {
    path: 'profile',
    loadComponent: () =>
      import('../profile/profile/profile.component').then(
        (m) => m.ProfileComponent
      ),
  },
  {
    path: 'edit-profile',
    loadComponent: () =>
      import('../profile/edit-profile/edit-profile.component').then(
        (m) => m.EditProfileComponent
      ),
  },
];
