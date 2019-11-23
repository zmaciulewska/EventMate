import { EventForm } from './../domain/event-form';
import { element } from 'protractor';
import { Component, OnInit, Input } from '@angular/core';
import { EventService } from '../services/event.service';
import { Event } from '../domain/event';
import { Router } from '../../../node_modules/@angular/router';

@Component({
  selector: 'app-admin-event-list',
  templateUrl: './admin-event-list.component.html',
  styleUrls: ['./admin-event-list.component.css']
})
export class AdminEventListComponent implements OnInit {

  @Input()
  public areEventsNotConfirmed: boolean;

  events: Event[];
  errorMessage: string;
  /*  errorConfirmMessage: string; */

  eventForm: EventForm;
  updatedEvent: Event;

  constructor(private eventService: EventService,
    private router: Router) { }

  ngOnInit() {
    if (this.areEventsNotConfirmed) {
      this.eventService.getAllNotConfirmed().subscribe(data => {
        this.events = data;
      }, error => {
        this.errorMessage = error;
      }
      );
    }
  }

  confirmEvent(confirmedEvent: Event) {

    this.updatedEvent = confirmedEvent;
    this.eventService.confirmEvent(confirmedEvent.id, this.updatedEvent).subscribe(data => {
      console.log(data);
      // this.router.navigate(['/events/details/', confirmedEvent.id]);
    }, error => {
      this.errorMessage = error;
    }
    );

  }

}
