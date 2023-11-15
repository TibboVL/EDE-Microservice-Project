import { Injectable } from '@angular/core';
import { Cookie } from 'ng2-cookies';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError } from 'rxjs';

@Injectable()
export class AppService {
  public clientId =
    '466655062537-ocs19qdjbpdkupnpi0ff86lq3gofngis.apps.googleusercontent.com';
  public redirectUri = 'http://localhost:8080/';

  constructor(private _http: HttpClient) {}

  retrieveToken(code: any) {
    let params = new URLSearchParams();
    params.append('grant_type', 'authorization_code');
    params.append('client_id', this.clientId);
    params.append('redirect_uri', this.redirectUri);
    params.append('code', code);

    let headers = new HttpHeaders({
      'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
      Authorization: 'Basic ' + btoa(this.clientId + ':secret'),
    });
    this._http
      .post(
        'http://localhost:8080/spring-security-oauth-server/oauth/token',
        params.toString(),
        { headers: headers }
      )
      .subscribe(
        (data) => this.saveToken(data),
        (err) => alert('Invalid Credentials')
      );
  }

  saveToken(token: any) {
    var expireDate = new Date().getTime() + 1000 * token.expires_in;
    Cookie.set('access_token', token.access_token, expireDate);
    console.log('Obtained Access token');
    window.location.href = 'http://localhost:8089';
  }

  getResource(resourceUrl: any): Observable<any> {
    var headers = new HttpHeaders({
      'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
      Authorization: 'Bearer ' + Cookie.get('access_token'),
    });
    return this._http.get(resourceUrl, { headers }).pipe(
      catchError((err: any) => {
        throw err;
      })
    );
  }

  checkCredentials() {
    return Cookie.check('access_token');
  }

  logout() {
    Cookie.delete('access_token');
    window.location.reload();
  }
}
