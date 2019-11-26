import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../domain/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userSampleUrl = 'http://localhost:8080/api/v1/sample/user';
  private adminUrl = 'http://localhost:8080/api/v1/sample/admin';
  private usersUrl = 'http://localhost:8080/api/v1/users';


  constructor(private http: HttpClient) { }

  getUserBoard(): Observable<string> {
    return this.http.get(this.userSampleUrl, { responseType: 'text' });
  }

  getAdminBoard(): Observable<string> {
    return this.http.get(this.adminUrl, { responseType: 'text' });
  }

  getAll() {
    return this.http.get<User[]> (this.usersUrl);
  }

  delete(id: number) {
    return this.http.delete(this.usersUrl + '/' + id);
  }
}
