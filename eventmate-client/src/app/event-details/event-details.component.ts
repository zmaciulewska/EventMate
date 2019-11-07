import { EventService } from './../services/event.service';
import { Component, OnInit } from '@angular/core';
import { Event } from '../domain/event';
import { ActivatedRoute, Router } from '../../../node_modules/@angular/router';

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.css']
})
export class EventDetailsComponent implements OnInit {

  event: Event;
  constructor(private route: ActivatedRoute,
    private eventService: EventService) { }

  ngOnInit() {
      this.route
      .params
      .subscribe(params => {
        this.eventService
        .getOneById(params['id'])
        .subscribe(data => {
          this.event = data;
        });
      });
  }

  deleteEvent(id: number) {
    this.eventService.delete(id).subscribe(res => console.log('Event deleted'));
  }


}
