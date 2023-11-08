import { Component } from '@angular/core';
import { AuthService } from './auth.service';
import { OAuthService } from 'angular-oauth2-oidc';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { authCodeFlowConfig } from './auth.config';
import { SongService } from './song.service';
import { Observable } from 'rxjs';
import { Song } from './models/song';
import { SongCardComponent } from './components/song-card/song-card.component';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { far } from '@fortawesome/free-regular-svg-icons';
import { fab } from '@fortawesome/free-brands-svg-icons';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  songs$: Observable<Song[]> = new Observable<Song[]>();

  /*   constructor(
    public oauthService: OAuthService,
    private http: HttpClient,
    private songService: SongService
  ) {} */

  constructor(
    library: FaIconLibrary,
    public oauthService: OAuthService,
    private http: HttpClient,
    private songService: SongService
  ) {
    library.addIconPacks(fas, far, fab);
  }

  ngOnInit(): void {
    this.oauthService.configure(authCodeFlowConfig); // Make sure to configure the OAuthService with your AuthConfig
    this.oauthService.loadDiscoveryDocumentAndTryLogin();

    this.songs$ = this.songService.getAllSongs();
  }

  /*   ngOnInit() {
    // this.oauthService.initCodeFlow();

    this.songService.getSong('1').subscribe((result) => {
      console.log(result);
    });
    this.songService.getAllSongs().subscribe((result) => {
      console.log(result);
    });
  } */

  login() {
    this.oauthService.initLoginFlow();
    // this.oauthService.initLoginFlowInPopup();
  }

  logout() {
    this.oauthService.logOut();
  }

  randomizeSongs() {
    this.songService.addDummyData();
  }

  makeApiRequest() {
    console.log(this.oauthService.getAccessToken());
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + this.oauthService.getAccessToken(),
    });

    // const apiUrl = 'https://api-tibbovl.cloud.okteto.net/user/all'; // Update this with your actual API endpoint
    const apiUrl = 'http://localhost:8080/user/all'; // Update this with your actual API endpoint

    this.http.get(apiUrl, { headers }).subscribe((response) => {
      // Handle the API response here
      console.log(response);
    });
  }
}
