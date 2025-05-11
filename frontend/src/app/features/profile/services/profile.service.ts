import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { UserProfile, UserProfileUpdate } from '../model/user-profile.model';

@Injectable({
  providedIn: 'root',
})
export class ProfileService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/api/users/profile`;

  /**
   * Get current user's profile information
   */
  getUserProfile(userId: number): Observable<UserProfile> {
    return this.http.get<UserProfile>(`${this.apiUrl}/${userId}`).pipe(
      catchError((error) => {
        console.error('Error fetching user profile:', error);
        return throwError(
          () => new Error('Failed to fetch user profile. Please try again.')
        );
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
        return throwError(
          () => new Error('Failed to update profile. Please try again.')
        );
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
