import { Component } from '@angular/core';
import { SongService } from '../services/song.service';
import { Observable } from 'rxjs';
import { Song } from './models/song';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { far } from '@fortawesome/free-regular-svg-icons';
import { fab } from '@fortawesome/free-brands-svg-icons';
import { OAuthService } from 'angular-oauth2-oidc';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  constructor(private oauthService: OAuthService, library: FaIconLibrary) {
    library.addIconPacks(fas, far, fab);
  }

  ngOnInit() {
    this.configureOAuth();
  }

  private configureOAuth() {
    this.oauthService.configure({
      issuer: 'https://accounts.google.com',
      redirectUri: environment.redirectUri,
      clientId:
        '466655062537-ocs19qdjbpdkupnpi0ff86lq3gofngis.apps.googleusercontent.com',
      showDebugInformation: true,
      scope: 'openid profile email',
      responseType: 'code id_token token', // Include 'id_token' in the response type
      strictDiscoveryDocumentValidation: false,
    });

    this.oauthService.loadDiscoveryDocument().then(() => {
      this.oauthService.tryLogin({});
    });
  }
}
