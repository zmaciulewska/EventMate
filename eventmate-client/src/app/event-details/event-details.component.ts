import { element } from 'protractor';
import { EventService } from '../services/event.service';
import { Component, OnInit } from '@angular/core';
import { Event } from '../domain/event';
import { ActivatedRoute, Router } from '@angular/router';
import { DateFormatPipe } from '../utils/DateFormatPipe';
import { CostService } from '../services/cost.service';
import { Cost } from '../domain/cost';
import { TokenStorageService } from '../auth/token-storage.service';

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.css']
})
export class EventDetailsComponent implements OnInit {

  event: Event;
  startDateString: String;
  endDateString: String;
  costs: Cost[];
  private authority: string;
  private roles: string[];

  isDeleteFailed = false;
  errorMessage = '';

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
          .getOneById(params['id'])
          .subscribe(data => {
            this.event = data;
            this.startDateString = this.dateFormatPipe.transform(new Date(this.event.startDate));
            this.endDateString = this.dateFormatPipe.transform(new Date(this.event.endDate));
            console.log(this.event);
          });
      });
    if (this.tokenStorage.getToken()) {
      this.roles = this.tokenStorage.getAuthorities();
      this.roles.every(role => {
        if (role === 'ROLE_ADMIN') {
          this.authority = 'admin';
          console.log('role: ' + this.authority);
          return false;
        }
        this.authority = 'user';
        console.log('role: ' + this.authority);
        return true;
      });
    }
  }

  deleteEvent(id: number) {
    if (confirm('Do you want to delete this event?')) {
      console.log('Delete confirmed');
      this.eventService.delete(id).subscribe(
        res => {
          console.log('Event deleted');
          this.router.navigate(['/home']);
        },
        error => {
          console.log('Event not deleted');
          this.isDeleteFailed = true;
          this.errorMessage = error.error.message;
        });
    }
  }
}
