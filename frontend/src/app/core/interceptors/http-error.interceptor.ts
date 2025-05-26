import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, EMPTY, throwError } from 'rxjs';

export const httpErrorInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);

  return next(req).pipe(
    catchError((error) => {
      // Handle the error here
      console.error('HTTP Error:', error);

      if (error.status === 401) {
        // redirect to login page
        router.navigateByUrl('/login');
        return EMPTY;
      }

      return throwError(() => error);
    })
  );
};
