import { Injectable } from '@angular/core';
import { ContactForm } from '../domain/contact-form';
import { HttpClient } from '../../../node_modules/@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  private contactUrl = 'http://localhost:8080/api/v1/contacts';

  constructor(private http: HttpClient) { }

  createContact(contactForm: ContactForm) {
    return this.http.post(this.contactUrl , contactForm);
  }

}
