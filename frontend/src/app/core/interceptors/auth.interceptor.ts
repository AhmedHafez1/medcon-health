import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

const AUTH_HEADER = 'Authorization';
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.token;

  if (token) {
    req = req.clone({
      setHeaders: {
        [AUTH_HEADER]: `Bearer ${token}`,
      },
    });
  }

  return next(req);
};
