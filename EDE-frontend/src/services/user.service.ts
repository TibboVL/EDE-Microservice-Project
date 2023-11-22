import { Injectable } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { BehaviorSubject, ReplaySubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  public user$: ReplaySubject<any> = new ReplaySubject<any>(1); // Set the buffer size to 1 to remember the last emitted value
  public user: any; // Accessible directly
  // public user: any;

  constructor(private oauthService: OAuthService) {
    this.getUserInfo();
  }

  getUserInfo(): void {
    const idToken = this.oauthService.getIdToken();

    if (idToken) {
      // Decode the ID token to access user information
      const decodedToken = this.decodeToken(idToken);
      // console.log(decodedToken);
      this.user = {
        sub: decodedToken.sub,
        email: decodedToken.email,
        name: decodedToken.name,
        picture: decodedToken.picture,
      };

      // Update the user information in the BehaviorSubject
      this.user$.next(this.user);
    }
  }

  decodeToken(token: string): any {
    const parts = token.split('.');
    const decodedPart = atob(parts[1]);
    return JSON.parse(decodedPart);
  }
}
