import { HttpClient, HttpHeaders } from '@angular/common/http';
import { EventEmitter, Injectable, Output } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { Observable } from 'rxjs';
import { Playlist, PlaylistFormModel } from 'src/app/models/playlist';
import { environment } from 'src/environments/environment';

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

  createPlaylist(playlistRequest: PlaylistFormModel): Observable<Playlist> {
    return this.http.post<Playlist>(this.baseUrl, playlistRequest, {
      headers: this.getHeaders(),
    });
  }

  getPlaylist(playlistId: string): Observable<Playlist> {
    return this.http.get<Playlist>(`${this.baseUrl}/${playlistId}`, {
      headers: this.getHeaders(),
    });
  }

  updatePlaylist(
    playlistId: string,
    playlistRequest: any
  ): Observable<Playlist> {
    return this.http.put<Playlist>(
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
    return this.http.post<Playlist>(
      `${this.baseUrl}/${userId}`,
      {},
      {
        headers: this.getHeaders(),
      }
    );
  }

  getPlaylistFromUser(userId: string): Observable<Playlist[]> {
    return this.http.get<Playlist[]>(`${this.baseUrl}/user/${userId}`, {
      headers: this.getHeaders(),
    });
  }

  addSong(playlistId: string, songId: string): Observable<Playlist> {
    return this.http.put<Playlist>(
      `${this.baseUrl}/${playlistId}/${songId}`,
      {},
      {
        headers: this.getHeaders(),
      }
    );
  }

  removeSong(playlistId: string, songId: string): Observable<Playlist> {
    return this.http.delete<Playlist>(
      `${this.baseUrl}/${playlistId}/${songId}`,
      {
        headers: this.getHeaders(),
      }
    );
  }
}
