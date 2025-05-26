import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { tap } from 'rxjs';
import { Login } from '../../features/auth/model/login.model';
import { environment } from '../../../environments/environment';
import { Register } from '../../features/auth/model/register.model';
import {
  AuthResponse,
  User,
} from '../../features/auth/model/auth-response.model';
import { Router } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);

  private readonly apiUrl = environment.apiUrl + '/api/auth';
  private readonly tokenKey = 'medcon-token';
  private readonly userKey = 'user';

  /**
   * Log in to the system.
   *
   * @param loginData The object containing the user's email and password.
   * @return The result of the POST request to the login endpoint.
   */
  login(loginData: Login) {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, loginData).pipe(
      tap((res) => {
        localStorage.setItem(this.tokenKey, res.token);
        localStorage.setItem(this.userKey, JSON.stringify(res.user));
      })
    );
  }

  /**
   * Register a new user in the system.
   *
   * @param registerData The object to be used for registration.
   * @return The result of the POST request to the register endpoint.
   */
  register(registerData: Register) {
    return this.http
      .post<AuthResponse>(`${this.apiUrl}/register`, registerData)
      .pipe(tap((res) => localStorage.setItem(this.tokenKey, res.token)));
  }

  get token() {
    return localStorage.getItem(this.tokenKey);
  }

  get user() {
    return JSON.parse(localStorage.getItem(this.userKey) || '{}') as User;
  }

  logout() {
    localStorage.removeItem(this.tokenKey);
    this.router.navigate(['login']);
  }

  /**
   * Checks if a user is currently logged in by verifying the presence of a token.
   *
   * @return True if a token is present, indicating the user is logged in; otherwise, false.
   */

  isLoggedIn(): boolean {
    return !!this.token;
  }
}
