import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class PlaylistService {
  private baseUrl = environment.apiBaseURL + 'playlist';

  constructor(private http: HttpClient, private oauthService: OAuthService) {}

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      Authorization: 'Bearer ' + this.oauthService.getIdToken(),
    });
  }

  createPlaylist(playlistRequest: any): Observable<any> {
    return this.http.post<any>(this.baseUrl, playlistRequest);
  }

  getPlaylist(playlistId: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${playlistId}`);
  }

  updatePlaylist(playlistId: string, playlistRequest: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/${playlistId}`, playlistRequest);
  }

  deletePlaylist(playlistId: string): Observable<any> {
    console.log('trying to delete:' + playlistId);
    return this.http.delete<any>(`${this.baseUrl}/${playlistId}`, {
      headers: this.getHeaders(),
    });
  }

  getPlaylistFromUser(userId: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/user/${userId}`);
  }
}
