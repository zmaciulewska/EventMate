import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { JwtResponse } from './jwt-response';
import { AuthLoginInfo } from './login-info';
import { SignUpInfo } from './signup-info';
import { User } from '../domain/user';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loginUrl = 'http://localhost:8080/api/v1/auth/signin';
  private signupUrl = 'http://localhost:8080/api/v1/auth/signup';
  private currentUserUrl = 'http://localhost:8080/api/v1/auth/user';

  constructor(private http: HttpClient) { }

  attemptAuth(credentials: AuthLoginInfo): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(this.loginUrl, credentials, httpOptions);
  }

  signUp(info: SignUpInfo): Observable<string> {
    console.log('SignupForm:' + SignUpInfo.toString());
    return this.http.post<string>(this.signupUrl, info, httpOptions);
  }

  getCurrentUser() {
    return this.http.get<User>(this.currentUserUrl);
  }

}
