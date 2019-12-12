import { UserService } from './../services/user.service';
import { Component, OnInit } from '@angular/core';
import { User } from '../domain/user';
import { Router, ActivatedRoute } from '../../../node_modules/@angular/router';
import { DateFormatPipe } from '../utils/DateFormatPipe';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  user: User;

  errorMessage: string;
  isEditShowcaseShown = false;


  areUserEventsShown = false;
  areUserEventOffersShown = false;
  areUserContactsShown = false;


  constructor(private userService: UserService,
    private router: Router,
    private route: ActivatedRoute,
    private dateFormatPipe: DateFormatPipe) { }

  ngOnInit() {
    this.isEditShowcaseShown = false;
    this.areUserEventsShown = false;
    this.areUserEventOffersShown = false;

    this.route
      .params
      .subscribe(params => {
        this.userService
          .getOneById(params['id'])
          .subscribe(data => {
            this.user = data;
            // this.birthDateString = this.dateFormatPipe.transform(new Date(this.user.showcase.birthDate));
          },
            error => {
              this.errorMessage = error.error.message;
            });
      });
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
