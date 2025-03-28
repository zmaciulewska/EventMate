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


  arePublishedEventsShown = false;

  areCategoriesShown = false;

  areUsersShown = false;

  areStatisticsShown = false;



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
    this.hidePublishedEvents();
    this.hideCategories();
    this.hideUsers();
    this.hideStatistics();
  }

  hideNotConfirmedEvents() {
    this.areNotConfirmedEventsShown = false;
  }

  showPublishedEvents() {
    this.arePublishedEventsShown = true;
    this.hideNotConfirmedEvents();
    this.hideCategories();
    this.hideUsers();
    this.hideStatistics();
  }

  hidePublishedEvents() {
    this.arePublishedEventsShown = false;
  }

  showCategories() {
    this.areCategoriesShown = true;
    this.hideNotConfirmedEvents();
    this.hidePublishedEvents();
    this.hideUsers();
    this.hideStatistics();
  }

  hideCategories() {
    this.areCategoriesShown = false;
  }

  showUsers() {
    this.areUsersShown = true;
    this.hideNotConfirmedEvents();
    this.hideCategories();
    this.hidePublishedEvents();
    this.hideStatistics();
  }

  hideUsers() {
    this.areUsersShown = false;
  }

  showStatistics() {
    this.areStatisticsShown = true;
    this.hideNotConfirmedEvents();
    this.hideCategories();
    this.hidePublishedEvents();
    this.hideUsers();
  }

  hideStatistics() {
    this.areStatisticsShown = false;
  }
}
