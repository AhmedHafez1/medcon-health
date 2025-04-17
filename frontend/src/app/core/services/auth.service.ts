import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs';
import { Login } from '../../shared/models/login.model';
import { environment } from '../../../environments/environment';
import { Register } from '../../shared/models/register.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly apiUrl = environment.apiUrl + '/api/auth';
  private readonly tokenKey = 'medcon-token';

  constructor(private http: HttpClient) {}

  login(loginData: Login) {
    return this.http
      .post<{ token: string }>(`${this.apiUrl}/login`, loginData)
      .pipe(tap((res) => localStorage.setItem(this.tokenKey, res.token)));
  }

  register(registerData: Register) {
    return this.http.post(`${this.apiUrl}/register`, registerData);
  }

  get token() {
    return localStorage.getItem(this.tokenKey);
  }

  logout() {
    localStorage.removeItem(this.tokenKey);
  }

  isLoggedIn(): boolean {
    return !!this.token;
  }
}
