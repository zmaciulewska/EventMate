import { EventOffer } from './../domain/event-offer';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../domain/user';
import { Showcase } from '../domain/showcase';
import { Event } from '../domain/event';
import { Contact } from '../domain/contact';
import { Page } from '../domain/page';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userSampleUrl = 'http://localhost:8080/api/v1/sample/user';
  private adminUrl = 'http://localhost:8080/api/v1/sample/admin';
  private usersUrl = 'http://localhost:8080/api/v1/users';


  private requestUrl: string;
  private params: HttpParams;
  private paramUrl: string;


  constructor(private http: HttpClient) { }

  getAllPaginated(page: number, size: number, searchParameters: Map<String, Object>) {
    this.params = new HttpParams();
    console.log(searchParameters);
    this.paramUrl = '';

    searchParameters.forEach((value: Object, key: string) => {
      if (value !== undefined) {
        this.params.set(key, value.toString());
        console.log(value.toString());
        this.paramUrl = this.paramUrl + '&' + key + '=' + value.toString();
      }
    });
    this.requestUrl = this.usersUrl + '?page=' + page + '&size=' + size + this.paramUrl;
    console.log(this.requestUrl);
    return this.http.get<Page<User>>(this.requestUrl);
  }

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


  getUserEvents(id: number, page: number, size: number, searchParameters: Map<String, Object>) {

    this.params = new HttpParams();
    console.log(searchParameters);
    this.paramUrl = '';

    searchParameters.forEach((value: Object, key: string) => {
      if (value !== undefined) {
        this.params.set(key, value.toString());
        console.log(value.toString());
        this.paramUrl = this.paramUrl + '&' + key + '=' + value.toString();
      }
    });
    this.requestUrl = this.usersUrl + '/' + id + '/events' + '?page=' + page + '&size=' + size + this.paramUrl;

    // this.requestUrl = this.requestUrl + this.paramUrl;

    console.log(this.requestUrl);


    return this.http.get<Page<Event>>(this.requestUrl);
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
