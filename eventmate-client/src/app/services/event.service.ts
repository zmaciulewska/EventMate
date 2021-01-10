import { EventOfferForm } from './../domain/event-offer-form';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Event } from '../domain/event';
import { EventForm } from '../domain/event-form';
import { EventOffer } from '../domain/event-offer';
import { Page } from '../domain/page';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private baseUrl = 'http://localhost:8080/api/v1/events';
  private eventOfferUrl = 'http://localhost:8080/api/v1/event-offers';

  private requestUrl: string;
  private params: HttpParams;
  private paramUrl: string;

  constructor(private http: HttpClient) { }

  getAll(page: number, size: number, searchParameters: Map<String, Object>) {
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
    this.requestUrl = this.baseUrl + '?page=' + page + '&size=' + size + this.paramUrl;

   // this.requestUrl = this.requestUrl + this.paramUrl;

    console.log(this.requestUrl);
    return this.http.get<Page<Event>>(this.requestUrl);
  }

  /* getAllConfirmedOrPrivate() {
    return this.http.get<Event[]>(this.baseUrl + '/confirmed-private');
  } */

  getAllNotConfirmed() {
    return this.http.get<Event[]>(this.baseUrl + '/not-confirmed');
  }

  confirmEvent(id: number, event: Event) {
    return this.http.put(this.baseUrl + '/' + id + '/confirm', event);
  }

  getOneById(id: number) {
    return this.http.get<Event>(this.baseUrl + '/' + id);
  }

  add(eventForm: EventForm) {
    return this.http.post(this.baseUrl, eventForm);
  }

  delete(id: number) {
    return this.http.delete(this.baseUrl + '/' + id);
  }

  update(id: number, eventForm: EventForm) {
    return this.http.put(this.baseUrl + '/' + id, eventForm);
  }

  addEventOffer(id: number, eventOfferForm: EventOfferForm) {
    return this.http.post(this.baseUrl + '/' + id + '/event-offers', eventOfferForm);
  }

  getAllEventOffers(id: number) {
    return this.http.get<EventOffer[]>(this.baseUrl + '/' + id + '/event-offers');
  }

  deleteEventOffer(eventOfferId: number) {
    return this.http.delete(this.eventOfferUrl + '/' + eventOfferId);
  }
}
