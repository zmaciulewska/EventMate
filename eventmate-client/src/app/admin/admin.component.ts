import { EventService } from './../services/event.service';
import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { Event } from '../domain/event';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  board: string;
  errorMessage: string;

  areNotConfirmedEventsShown = false;
  notConfirmedEvents: Event[];
  notConfirmedEventsError: string;



  constructor(private userService: UserService,
  private eventService: EventService) { }

  ngOnInit() {
    this.userService.getAdminBoard().subscribe(
      data => {
        this.board = data;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    );
  }


  showNotConfirmedEvents() {
    this.areNotConfirmedEventsShown = true;
    this.eventService.getAllNotConfirmed().subscribe(
      data => {
        this.notConfirmedEvents = data;
      },
      error => {
        this.notConfirmedEventsError = error;
      }
    );
  }

  hideNotConfirmedEvents() {
    this.areNotConfirmedEventsShown = false;
  }
}
