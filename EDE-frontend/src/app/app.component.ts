import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
// import { authCodeFlowConfig } from './auth.config';
import { SongService } from './song.service';
import { Observable } from 'rxjs';
import { Song } from './models/song';
import { SongCardComponent } from './components/song-card/song-card.component';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { far } from '@fortawesome/free-regular-svg-icons';
import { fab } from '@fortawesome/free-brands-svg-icons';
import { AppService } from './app.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  /*   songs$: Observable<Song[]> = new Observable<Song[]>();

  constructor(library: FaIconLibrary, private songService: SongService) {
    library.addIconPacks(fas, far, fab);
  }

  ngOnInit() {
    this.songService.getSong('1').subscribe((result) => {
      console.log(result);
    });
    this.songs$ = this.songService.getAllSongs();
  }

  randomizeSongs() {
    this.songService.addDummyData();
  }

  makeApiRequest() {
    this.songService.deleteSong('1').subscribe((Response) => {
      console.log(Response);
    });
  } */

  public isLoggedIn = false;

  constructor(private _service: AppService) {}

  ngOnInit() {
    this.isLoggedIn = this._service.checkCredentials();
    let i = window.location.href.indexOf('code');
    if (!this.isLoggedIn && i != -1) {
      this._service.retrieveToken(window.location.href.substring(i + 5));
    }
  }

  login() {
    window.location.href =
      'http://localhost:8080/spring-security-oauth-server/oauth/authorize?response_type=code&client_id=' +
      this._service.clientId +
      '&redirect_uri=' +
      this._service.redirectUri;
  }

  logout() {
    this._service.logout();
  }
}
