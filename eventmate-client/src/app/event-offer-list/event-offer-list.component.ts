import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '../../../node_modules/@angular/router';
import { EventService } from '../services/event.service';
import { DateFormatPipe } from '../utils/DateFormatPipe';
import { TokenStorageService } from '../auth/token-storage.service';
import { EventOffer } from '../domain/event-offer';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-event-offer-list',
  templateUrl: './event-offer-list.component.html',
  styleUrls: ['./event-offer-list.component.css']
})
export class EventOfferListComponent implements OnInit {

  eventOffers: EventOffer[];
  emptyList = true;

  @Input()
  userOffers = false;

  constructor(private route: ActivatedRoute,
    private eventService: EventService,
    private userService: UserService,
    private dateFormatPipe: DateFormatPipe,
    private tokenStorage: TokenStorageService,
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
  }

}

