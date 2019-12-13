import { EventOffer } from './../domain/event-offer';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../domain/user';
import { Showcase } from '../domain/showcase';
import { Event } from '../domain/event';
import { Contact } from '../domain/contact';

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
    return this.http.get<User[]>(this.usersUrl);
  }

  delete(id: number) {
    return this.http.delete(this.usersUrl + '/' + id);
  }

  getOneById(id: number) {
    return this.http.get<User>(this.usersUrl + '/' + id);
  }

  update(id: number, showcaseForm: Showcase) {
    return this.http.put(this.usersUrl + '/' + id + '/showcase', showcaseForm);
  }

  getUserEvents(id: number) {
    return this.http.get<Event[]>(this.usersUrl + '/' + id + '/events');
  }


  getUserEventOffers(id: number) {
    return this.http.get<EventOffer[]>(this.usersUrl + '/' + id + '/event-offers');
  }

  getUserShowcase(id: number) {
    return this.http.get(this.usersUrl + '/' + id + '/showcase');
  }

  getUserContacts(id: number) {
    return this.http.get<Contact[]>(this.usersUrl + '/' + id + '/contacts');
  }
}
