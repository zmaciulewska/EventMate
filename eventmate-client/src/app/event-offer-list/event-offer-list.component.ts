import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '../../../node_modules/@angular/router';
import { EventService } from '../services/event.service';
import { DateFormatPipe } from '../utils/DateFormatPipe';
import { TokenStorageService } from '../auth/token-storage.service';
import { EventOffer } from '../domain/event-offer';

@Component({
  selector: 'app-event-offer-list',
  templateUrl: './event-offer-list.component.html',
  styleUrls: ['./event-offer-list.component.css']
})
export class EventOfferListComponent implements OnInit {

  eventOffers: EventOffer[];
  constructor(private route: ActivatedRoute,
    private eventService: EventService,
    private dateFormatPipe: DateFormatPipe,
    private tokenStorage: TokenStorageService,
    private router: Router) { }

  ngOnInit() {
    this.route
      .params
      .subscribe(params => {
        this.eventService
          .getAllEventOffers(params['id'])
          .subscribe(data => {
            this.eventOffers = data;
          });
      });
  }

}
