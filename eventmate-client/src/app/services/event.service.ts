import { EventOfferForm } from './../domain/event-offer-form';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Event } from '../domain/event';
import { EventForm } from '../domain/event-form';
import { EventOffer } from '../domain/event-offer';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private baseUrl = 'http://localhost:8080/api/v1/events';

  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get<Event[]>(this.baseUrl);
  }

  getAllConfirmedOrPrivate() {
    return this.http.get<Event[]>(this.baseUrl + '/confirmed-private');
  }

  getAllNotConfirmed() {
    return this.http.get<Event[]>(this.baseUrl + '/not-confirmed');
  }

  confirmEvent(id: number, event: Event ) {
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

  update(id: number, eventForm: EventForm ) {
    return this.http.put(this.baseUrl + '/' + id, eventForm);
  }

  addEventOffer(id: number, eventOfferForm: EventOfferForm) {
    return this.http.post(this.baseUrl + '/' + id + '/event-offers', eventOfferForm);
  }

  getAllEventOffers(id: number) {
    return this.http.get<EventOffer[]>(this.baseUrl + '/' + id + '/event-offers');
  }

}
