import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { format } from 'date-fns';
import {
  BehaviorSubject,
  Observable,
  ReplaySubject,
  catchError,
  throwError,
} from 'rxjs';
import { User } from 'src/app/models/user';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  public user$: ReplaySubject<any> = new ReplaySubject<any>(1); // Set the buffer size to 1 to remember the last emitted value
  public user: any; // Accessible directly
  // public user: any;

  constructor(private http: HttpClient, private oauthService: OAuthService) {
    this.getUserInfo();
  }

  private baseUrl = environment.apiBaseURL + 'user';

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      Authorization: 'Bearer ' + this.oauthService.getIdToken(),
    });
  }

  getUserInfo(): void {
    const idToken = this.oauthService.getIdToken();

    if (idToken) {
      // Decode the ID token to access user information
      const decodedToken = this.decodeToken(idToken);
      // console.log(decodedToken);
      this.user = {
        id: undefined,
        sub: decodedToken.sub,
        email: decodedToken.email,
        username: decodedToken.name,
        picture: decodedToken.picture,
        firstname: decodedToken.given_name,
        lastname: decodedToken.family_name,
      };
      // console.log(this.user);

      // Update the user information in the BehaviorSubject
      this.user$.next(this.user);

      this.getUserFromAuthId(this.user.sub)
        .pipe(
          catchError((error: any) => {
            if (error.status === 404) {
              console.log('User not found in the database.');
              // console.log(this.user);
              this.createUser({
                userId: decodedToken.sub,
                email: decodedToken.email,
                username: decodedToken.name,
                picture: decodedToken.picture,
                firstname: decodedToken.given_name,
                lastname: decodedToken.family_name,
                dateOfBirth: format(Date.now(), 'yyyy-MM-dd'),
              }).subscribe((result) => {
                this.user.id = result.id;
                this.user$.next(this.user);
              });
              // Handle the case where the user is not found, e.g., display a message or redirect to a registration page
              return throwError('User not found.');
            } else {
              // Handle other errors as needed
              return throwError('Error fetching user information.');
            }
          })
        )
        .subscribe((result) => {
          console.log('user already in db:');
          this.user.id = result.id;
          console.log(result.id);
          this.user$.next(this.user);
        });
    }
  }

  decodeToken(token: string): any {
    const parts = token.split('.');
    const decodedPart = atob(parts[1]);
    return JSON.parse(decodedPart);
  }

  createUser(userRequest: any): Observable<any> {
    return this.http.post<any>(this.baseUrl, userRequest, {
      headers: this.getHeaders(),
    });
  }

  getUser(userId: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${userId}`, {
      headers: this.getHeaders(),
    });
  }

  getUserFromAuthId(authId: string): Observable<User> {
    return this.http.get<any>(`${this.baseUrl}/checkExistence/${authId}`, {
      headers: this.getHeaders(),
    });
  }
}
