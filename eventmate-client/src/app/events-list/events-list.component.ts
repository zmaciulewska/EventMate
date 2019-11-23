import { EventService } from '../services/event.service';
import { Event } from '../domain/event';
import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../auth/token-storage.service';

@Component({
  selector: 'app-events-list',
  templateUrl: './events-list.component.html',
  styleUrls: ['./events-list.component.css']
})
export class EventsListComponent implements OnInit {

  events: Event[];
  private roles: string[];
  private authority: string;

  constructor(private eventService: EventService, private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    this.getAllEvents();

    if (this.tokenStorage.getToken()) {
      this.roles = this.tokenStorage.getAuthorities();
      this.roles.every(role => {
        if (role === 'ROLE_ADMIN') {
          this.authority = 'admin';
          return false;
        } else if (role === 'ROLE_PM') {
          this.authority = 'pm';
          return false;
        }
        this.authority = 'user';
        return true;
      });
    }
  }

  getAllEvents(): void {
    this.eventService.getAllConfirmedOrPrivate().subscribe(data => {
      this.events = data;
    });
  }

}
