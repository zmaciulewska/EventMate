import { ContactsListComponent } from './../contacts-list/contacts-list.component';
import { UserService } from './../services/user.service';
import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { User } from '../domain/user';
import { Router, ActivatedRoute } from '../../../node_modules/@angular/router';
import { DateFormatPipe } from '../utils/DateFormatPipe';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  user: User;
  userId: number;

  errorMessage: string;
  isEditShowcaseShown = false;


  areUserEventsShown = false;
  areUserEventOffersShown = false;
  areUserContactsShown = false;

  @Input()
  adminMode = true;

  constructor(private userService: UserService,
    private router: Router,
    private route: ActivatedRoute,
    private dateFormatPipe: DateFormatPipe,
    private authService: AuthService) { }

  ngOnInit() {
    this.isEditShowcaseShown = false;
    this.areUserEventsShown = false;
    this.areUserEventOffersShown = false;

    if (this.adminMode) {
      console.log('dupa');
      this.route
        .params
        .subscribe(params => {
          this.userService
            .getOneById(params['id'])
            .subscribe(data => {
              this.user = data;
              this.userId = data.id;
              // this.birthDateString = this.dateFormatPipe.transform(new Date(this.user.showcase.birthDate));
            },
              error => {
                this.errorMessage = error.error.message;
              });
        });
    } else {
      this.authService.getCurrentUser().subscribe(
        data => {
          this.user = data;
          this.userId = data.id;
          console.log('current usr: ' + this.user);
        },
        error => {
          this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
        });
    }

  }

  showForm() {
    this.isEditShowcaseShown = true;
  }

  hideForm() {
    this.isEditShowcaseShown = false;
  }

  showUserEvents() {
    this.areUserEventsShown = true;
  }

  hideUserEvents() {
    this.areUserEventsShown = false;
  }

  showUserEventOffers() {
    this.areUserEventOffersShown = true;
  }

  hideUserEventOffers() {
    this.areUserEventOffersShown = false;
  }

  showUserContacts() {
    this.areUserContactsShown = true;
  }

  hideUserContacts() {
    this.areUserContactsShown = false;
  }

}
