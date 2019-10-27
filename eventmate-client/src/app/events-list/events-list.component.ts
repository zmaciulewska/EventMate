import { EventService } from './../services/event.service';
import { Event } from './../domain/event';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-events-list',
  templateUrl: './events-list.component.html',
  styleUrls: ['./events-list.component.css']
})
export class EventsListComponent implements OnInit {

  events: Event[];

  constructor(private eventService: EventService) { }

  ngOnInit() {
    this.getAllEvents();
  }

  getAllEvents(): void {
    this.eventService.getAll().subscribe(data => {
      this.events = data;
    });
  }

}
