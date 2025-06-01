import { HttpClient, HttpResponse } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { EMPTY, Observable, catchError, map, throwError } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { UserProfile, UserProfileUpdate } from '../models/user-profile.model';

@Injectable({
  providedIn: 'root',
})
export class ProfileService {
  private http = inject(HttpClient);

  private apiUrl = `${environment.apiUrl}/api/users/profile`;

  /**
   * Get current user's profile information
   */
  getUserProfile(userId: number) {
    return this.http
      .get<UserProfile>(`${this.apiUrl}/${userId}`, {
        observe: 'response',
      })
      .pipe(
        map((response: HttpResponse<UserProfile>) => {
          return response.body!;
        }),
        catchError((error) => {
          console.error('Error fetching user profile:', error);
          return throwError(() => error);
        })
      );
  }

  /**
   * Update user profile information
   */
  updateUserProfile(profile: UserProfileUpdate): Observable<UserProfile> {
    return this.http.post<UserProfile>(this.apiUrl, profile).pipe(
      catchError((error) => {
        console.error('Error updating user profile:', error);
        return EMPTY;
      })
    );
  }

  /**
   * Upload profile image
   */
  uploadProfileImage(file: File): Observable<{ profileImageUrl: string }> {
    const formData = new FormData();
    formData.append('file', file);

    return this.http
      .post<{ profileImageUrl: string }>(`${this.apiUrl}/image`, formData)
      .pipe(
        catchError((error) => {
          console.error('Error uploading profile image:', error);
          return throwError(
            () => new Error('Failed to upload image. Please try again.')
          );
        })
      );
  }
}
