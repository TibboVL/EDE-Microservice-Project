import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Token } from '@angular/compiler';
import { EventEmitter, Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SongService {
  // private baseUrl = 'http://api-tibbovl.cloud.okteto.net/api/library/song';
  private baseUrl = 'http://localhost:8080/song';
  private myToken = '';
  private intervalId: any;

  constructor(private http: HttpClient) {
    this.intervalId = setInterval(() => {
      const token = localStorage.getItem('token');
      if (token) {
        this.myToken = token;
        console.log(this.myToken);
        clearInterval(this.intervalId); // Stop the interval once the token is found
      } else {
        console.log('test');
      }
    }, 5000);
  }

  createSong(songRequest: any): Observable<any> {
    return this.http
      .post<any>(this.baseUrl, songRequest)
      .pipe(catchError(this.handleError));
  }

  getSong(songId: string): Observable<any> {
    return this.http
      .get<any>(`${this.baseUrl}/${songId}`)
      .pipe(catchError(this.handleError));
  }

  updateSong(songId: string, songRequest: any): Observable<any> {
    return this.http
      .put<any>(`${this.baseUrl}/${songId}`, songRequest)
      .pipe(catchError(this.handleError));
  }

  deleteSong(songId: string): Observable<any> {
    return this.http
      .delete<any>(`${this.baseUrl}/${songId}`, {
        headers: new HttpHeaders({
          Authorization: `Bearer ${this.myToken}`,
        }),
      })
      .pipe(catchError(this.handleError));
  }

  getAllSongs(): Observable<any> {
    return this.http
      .get<any>(`${this.baseUrl}/all`)
      .pipe(catchError(this.handleError));
  }

  addDummyData(): Observable<any> {
    return this.http
      .get<any>(`${this.baseUrl}/dummyData`)
      .pipe(catchError(this.handleError));
  }

  // Additional methods for future endpoints
  getRatings(songId: string): Observable<any> {
    return this.http
      .get<any>(`${this.baseUrl}/ratings/${songId}`)
      .pipe(catchError(this.handleError));
  }

  updateRatings(songId: string, rating: number): Observable<any> {
    return this.http
      .put<any>(`${this.baseUrl}/ratings/${songId}?rating=${rating}`, {})
      .pipe(catchError(this.handleError));
  }

  // Error handling
  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, ` + `body was: ${error.error}`
      );
    }
    // Return an observable with a user-facing error message.
    return throwError('Something bad happened; please try again later.');
  }
}
