import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Subject, tap, throwError } from 'rxjs';
import { AuthResponseData } from '../dto/auth-response-data';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  user = new Subject<User>();

  constructor(private http: HttpClient) {}

  login(login: string, password: string) {
    return this.http
      .post<AuthResponseData>('http://localhost:8080/api/auth/login', {
        username: login,
        password: password,
      })
      .pipe(
        catchError(this.handleError),
        tap((resData) => {
          const expirationDate = new Date(
            new Date().getTime() + +resData.expiresAt * 1000
          );
          const user = new User(
            resData.token,
            resData.username,
            resData.refreshToken,
            expirationDate
          );
          this.user.next(user);
        })
      );
  }

  handleError(errorRes: HttpErrorResponse) {
    const errorMessage = 'Authentication failed';
    return throwError(() => new Error(errorMessage));
  }
}
