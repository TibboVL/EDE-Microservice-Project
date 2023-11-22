import { HttpClient, HttpHeaders } from '@angular/common/http';
import { EventEmitter, Injectable, Output } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { Observable } from 'rxjs';
import { Playlist } from 'src/app/models/playlist';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class PlaylistService {
  private baseUrl = environment.apiBaseURL + 'playlist';

  @Output() playlistsModified = new EventEmitter();

  constructor(private http: HttpClient, private oauthService: OAuthService) {}

  private getHeaders(): HttpHeaders {
    if (this.oauthService.getIdToken() != null) {
      return new HttpHeaders({
        Authorization: 'Bearer ' + this.oauthService.getIdToken(),
      });
    } else {
      throw console.error('TOKEN NOT LOADED');
    }
  }

  createPlaylist(playlistRequest: any): Observable<any> {
    return this.http.post<any>(this.baseUrl, playlistRequest, {
      headers: this.getHeaders(),
    });
  }

  getPlaylist(playlistId: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${playlistId}`, {
      headers: this.getHeaders(),
    });
  }

  updatePlaylist(playlistId: string, playlistRequest: any): Observable<any> {
    return this.http.put<any>(
      `${this.baseUrl}/${playlistId}`,
      playlistRequest,
      { headers: this.getHeaders() }
    );
  }

  deletePlaylist(playlistId: string): Observable<any> {
    console.log('trying to delete:' + playlistId);
    return this.http.delete<any>(`${this.baseUrl}/${playlistId}`, {
      headers: this.getHeaders(),
    });
  }

  getMyFavoritesPlaylist(userId: string): Observable<Playlist> {
    return this.http.post<any>(
      `${this.baseUrl}/${userId}`,
      {},
      {
        headers: this.getHeaders(),
      }
    );
  }

  getPlaylistFromUser(userId: string): Observable<Playlist[]> {
    return this.http.get<any>(`${this.baseUrl}/user/${userId}`, {
      headers: this.getHeaders(),
    });
  }
}
