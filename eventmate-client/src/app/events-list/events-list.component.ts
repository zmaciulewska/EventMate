import { EventSearchForm } from './event-search-form';
import { EventService } from '../services/event.service';
import { Component, OnInit, Input } from '@angular/core';
import { TokenStorageService } from '../auth/token-storage.service';
import { Router, ActivatedRoute } from '../../../node_modules/@angular/router';
import { UserService } from '../services/user.service';
import { Event } from '../domain/event';
import { Page } from '../domain/page';

@Component({
  selector: 'app-events-list',
  templateUrl: './events-list.component.html',
  styleUrls: ['./events-list.component.css']
})
export class EventsListComponent implements OnInit {

  currentPage = 0;
  pageSize = 5;

  /*  prevPage = 'Poprzednia strona';
   nextPage = 'Następna strona'; */
  /* pages: Array<number>; */
  events: Event[];
  private roles: string[];
  private authority: string;

  currentData: Page<Event>;

  eventsConfig: any;

  searchForm: EventSearchForm;
  tmpStartDateTime: Date;
  tmpEndDateTime: Date;

  parameterMap: Map<String, Object>;

  isError = false;
  errorMessage: string;

  isSuccess = false;
  successMessage: string;

  @Input()
  public areEventsNotConfirmed = false;
  @Input()
  public isDisplayOnly = true;
  @Input()
  public userEvents = false;
  @Input() userId: number;

  constructor(private eventService: EventService,
    private tokenStorage: TokenStorageService,
    private router: Router,
    private userService: UserService,
    private route: ActivatedRoute) {
  }

  clearFilters() {
    this.prepareSerachForm();
    this.loadEvents();
  }

  prepareSerachForm() {
    this.searchForm = new EventSearchForm();
    // this.tmpStartDateTime = new Date();
    // this.tmpEndDateTime = new Date();
  }


  loadEvents() {
    this.prepareSearchCriteria();
    if (this.userEvents) {
      this.getUserEvents();
    } else {
      if (this.areEventsNotConfirmed) {
        this.getNotConfirmedEvents();
      } else {
        this.getPublishedEvents();
      }
    }
  }

  prepareSearchCriteria() {
    this.parameterMap = new Map();

    if (this.tmpStartDateTime !== undefined) { this.searchForm.startDate = this.tmpStartDateTime + ':00.000'; }
    if (this.tmpEndDateTime !== undefined) { this.searchForm.endDate = this.tmpEndDateTime + ':00.000'; }

    /*    if (this.tmpStartDateTime.toString().indexOf('-') !== -1) {
         this.searchForm.startDate = this.tmpStartDateTime + ':00.000';
       }
       if (this.tmpEndDateTime.toString().indexOf('-') !== -1) {
         this.searchForm.endDate = this.tmpEndDateTime + ':00.000';
       }
    */
    this.parameterMap.set('title', this.searchForm.title);
    this.parameterMap.set('localization', this.searchForm.localization);
    this.parameterMap.set('startDate', this.searchForm.startDate);
    this.parameterMap.set('endDate', this.searchForm.endDate);
    this.parameterMap.set('categoryCode', this.searchForm.categoryCode);
    if (this.areEventsNotConfirmed || this.userEvents) {
      this.parameterMap.set('areConfirmed', false);
    } else {
      this.parameterMap.set('areConfirmed', true);
    }
  }

  ngOnInit() {
    this.prepareSerachForm();
    this.loadEvents();

    if (this.tokenStorage.getToken()) {
      this.roles = this.tokenStorage.getAuthorities();
      this.roles.every(role => {
        if (role === 'ROLE_ADMIN') {
          this.authority = 'admin';
          return false;
        }
        this.authority = 'user';
        return true;
      });
    }
  }

  getUserEvents(): void {
    if (this.userId === null) {
      this.route
        .params
        .subscribe(params => {
          this.userService.getUserEvents(params['id'], this.currentPage, this.pageSize, this.parameterMap).subscribe(data => {
            this.events = data.content;
            this.currentData = data;
            this.setConfig();
            console.log(this.currentData);
          },
            error => {
              this.isError = true;
              this.errorMessage = error.error.message;
            });
        });
    } else {
      this.userService
        .getUserEvents(this.userId, this.currentPage, this.pageSize, this.parameterMap)
        .subscribe(data => {
          this.events = data.content;
          this.currentData = data;
          this.setConfig();
          console.log(this.currentData);
        },
          error => {
            this.isError = true;
            this.errorMessage = error.error.message;
          });
    }

  }

  setConfig() {
    this.eventsConfig = {
      itemsPerPage: 5,
      currentPage: this.currentPage,
      totalItems: this.currentData.totalElements,
    };
  }

  getPublishedEvents(): void {
    this.eventService.getAll(this.currentPage - 1, this.pageSize, this.parameterMap).subscribe(data => {
      this.events = data.content;
      this.currentData = data;
      this.setConfig();
      console.log(this.currentData);
    },
      error => {
        this.isError = true;
        this.errorMessage = error;
      });
  }

  getNotConfirmedEvents(): void {
    this.eventService.getAll(this.currentPage, this.pageSize, this.parameterMap).subscribe(data => {
      this.events = data.content;
      this.currentData = data;
      this.setConfig();
      console.log(this.currentData);
    },
      error => {
        this.isError = true;
        this.errorMessage = error;
      });
    /*  this.eventService.getAllNotConfirmed().subscribe(data => {
       this.events = data;
     }, error => {
       this.errorMessage = error;
     }
     ); */
  }

  confirmEvent(confirmedEvent: Event) {
    this.eventService.confirmEvent(confirmedEvent.id, confirmedEvent).subscribe(data => {
      console.log(data);
      // window.location.reload();
      this.isSuccess = true;
      this.successMessage = 'Wydarzenie zostało zatwierdzone.';
      this.events = this.events.filter(item => item.id !== confirmedEvent.id);
     // this.router.navigate(['/events/details/', confirmedEvent.id]);
    }, error => {
      this.isError = true;
      this.errorMessage = error.error.message;
    }
    );
  }

  deleteEvent(id: number) {

    if (confirm('Na pewno chcesz usunąć te wydarzenie?')) {
      console.log('Delete confirmed');
      this.eventService.delete(id).subscribe(
        res => {
          console.log('Event deleted');
          this.isSuccess = true;
          this.successMessage = 'Wydarzenie zostało usunięte.';
          this.events = this.events.filter(item => item.id !== id);
        //  this.router.navigate(['/home']);
        },
        error => {
          console.log('Event not deleted');
          this.isError = true;
          this.errorMessage = error.error.message;
        });
    }
  }

  eventsPageChange(newPage: number) {
    this.currentPage = newPage;
    this.loadEvents();
  }
}
