import { ContactService } from './../services/contact.service';
import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '../../../node_modules/@angular/router';
import { EventService } from '../services/event.service';
import { DateFormatPipe } from '../utils/DateFormatPipe';
import { TokenStorageService } from '../auth/token-storage.service';
import { EventOffer } from '../domain/event-offer';
import { UserService } from '../services/user.service';
import { ContactForm } from '../domain/contact-form';

@Component({
  selector: 'app-event-offer-list',
  templateUrl: './event-offer-list.component.html',
  styleUrls: ['./event-offer-list.component.css']
})
export class EventOfferListComponent implements OnInit {

  eventOffers: EventOffer[];
  emptyList = true;

  contactForm: ContactForm = new ContactForm();
  // contactForm: any;

  @Input()
  userOffers = false;

  @Input() userId: number;

  errorMessage: string;
  isError = false;

  isSuccess = false;
  successMessage = '';

  isDeleteEventOffferFailed = false;
  deleteEventOfferErrorMessage = '';


  constructor(private route: ActivatedRoute,
    private eventService: EventService,
    private userService: UserService,
    private dateFormatPipe: DateFormatPipe,
    private tokenStorage: TokenStorageService,
    private contactService: ContactService,
    private router: Router) { }

  ngOnInit() {
    if (this.userOffers) {
      this.loadUserOffers();
    } else {
      this.loadEventOffers();
    }
  }

  loadEventOffers() {
    this.route
      .params
      .subscribe(params => {
        this.eventService
          .getAllEventOffers(params['id'])
          .subscribe(data => {
            this.eventOffers = data;
            if (this.eventOffers.length > 0) { this.emptyList = false; }
          });
      });
  }

  loadUserOffers() {
    if (this.userId === null) {
      this.route
        .params
        .subscribe(params => {
          this.userService
            .getUserEventOffers(params['id'])
            .subscribe(data => {
              this.eventOffers = data;
              if (this.eventOffers.length > 0) { this.emptyList = false; }
            });
        });
    } else {
      this.userService
        .getUserEventOffers(this.userId)
        .subscribe(data => {
          this.eventOffers = data;
          if (this.eventOffers.length > 0) { this.emptyList = false; }
        });
    }
  }

  createContact(eventOffer: EventOffer) {
    this.contactForm.secondUserId = eventOffer.ownerId;
    this.contactForm.eventId = eventOffer.eventId;

    this.contactService.createContact(this.contactForm).subscribe(
      data => {
        console.log(data);
        this.isSuccess = true;
        this.successMessage = 'Sukces! Nowy kontakt został nawiązany.';
      },
      error => {
        this.isError = true;
        this.errorMessage = error.error.message;
        console.log(error);
      }
    );
  }

  deleteEventOffer(eventOffer: EventOffer) {
    if (confirm('Na pewno chcesz usunąć ofertę?')) {
      console.log('Delete confirmed');
      this.eventService.deleteEventOffer(eventOffer.id).subscribe(
        res => {
          // console.log('Event deleted');
          this.eventOffers = this.eventOffers.filter(item => item !== eventOffer);
         // this.router.navigate(['/home']);
        },
        error => {
          // console.log('Event not deleted');
          this.isDeleteEventOffferFailed = true;
          this.deleteEventOfferErrorMessage = error.error.message;
        });
    }
  }
}

