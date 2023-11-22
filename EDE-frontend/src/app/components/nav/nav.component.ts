import { Component, isDevMode } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OAuthService } from 'angular-oauth2-oidc';
import { UserService } from 'src/services/user.service';

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss'],
})
export class NavComponent {
  public user: any;
  devMode: boolean = isDevMode();

  constructor(
    private oauthService: OAuthService,
    public userService: UserService
  ) {}

  ngOnInit() {
    this.userService.user$.subscribe((user) => {
      console.log(user);

      this.user = user;
    });
  }

  get isAuthenticated(): boolean {
    return this.oauthService.hasValidAccessToken();
  }

  login() {
    this.oauthService.initCodeFlow();
  }

  logout() {
    this.oauthService.logOut();
  }
}
