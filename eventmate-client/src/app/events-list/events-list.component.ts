import { EventService } from '../services/event.service';
import { Event } from '../domain/event';
import { Component, OnInit, Input } from '@angular/core';
import { TokenStorageService } from '../auth/token-storage.service';
import { Router } from '../../../node_modules/@angular/router';

@Component({
  selector: 'app-events-list',
  templateUrl: './events-list.component.html',
  styleUrls: ['./events-list.component.css']
})
export class EventsListComponent implements OnInit {

  events: Event[];
  private roles: string[];
  private authority: string;

  errorMessage: string;

  @Input()
  public areEventsNotConfirmed = false;
  @Input()
  public isDisplayOnly = true;

  constructor(private eventService: EventService,
    private tokenStorage: TokenStorageService,
    private router: Router) { }

  ngOnInit() {
    if (this.areEventsNotConfirmed) {
      this.getNotConfirmedEvents();
    } else {
      this.getPublishedEvents();
    }

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

  getPublishedEvents(): void {
    this.eventService.getAllConfirmedOrPrivate().subscribe(data => {
      this.events = data;
    },
  error => {
    this.errorMessage = error;
  });
  }

  getNotConfirmedEvents(): void {
    this.eventService.getAllNotConfirmed().subscribe(data => {
      this.events = data;
    }, error => {
      this.errorMessage = error;
    }
    );
  }

  confirmEvent(confirmedEvent: Event) {
    this.eventService.confirmEvent(confirmedEvent.id, confirmedEvent).subscribe(data => {
      console.log(data);
      this.router.navigate(['/events/details/', confirmedEvent.id]);
    }, error => {
      this.errorMessage = error;
    }
    );

  }

}
