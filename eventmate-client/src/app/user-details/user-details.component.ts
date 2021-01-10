import { ContactsListComponent } from './../contacts-list/contacts-list.component';
import { UserService } from './../services/user.service';
import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { User } from '../domain/user';
import { Router, ActivatedRoute } from '../../../node_modules/@angular/router';
import { DateFormatPipe } from '../utils/DateFormatPipe';
import { AuthService } from '../auth/auth.service';
import { TokenStorageService } from '../auth/token-storage.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  user: User;
  userId: number;

  isError = false;
  errorMessage: string;

  visitingUser: User;

  
  isEditShowcaseShown = false;


  areUserEventsShown = false;
  areUserEventOffersShown = false;
  areUserContactsShown = false;
  isVisitorOwner = false;

  areRestrictedInformationShown = false;

  private authority: string;
  private roles: string[];

  @Input()
  vistorMode = true;

  constructor(private userService: UserService,
    private router: Router,
    private route: ActivatedRoute,
    private dateFormatPipe: DateFormatPipe,
    private tokenStorage: TokenStorageService,
    private authService: AuthService) { }

  ngOnInit() {
    this.isEditShowcaseShown = false;
    this.areUserEventsShown = false;
    this.areUserEventOffersShown = false;

    if (this.vistorMode) {
      console.log('dupa');

      this.getVisitingUser();
      this.getVisitorRole();

      this.route
        .params
        .subscribe(params => {
          this.userService
            .getOneById(params['id'])
            .subscribe(data => {
              this.user = data;
              this.userId = data.id;

              if (this.visitingUser.id === this.userId) {
                this.areRestrictedInformationShown = true;
                this.isVisitorOwner = true;
              }

              if (this.authority === 'admin') {
                this.areRestrictedInformationShown = true;
              }

              // this.birthDateString = this.dateFormatPipe.transform(new Date(this.user.showcase.birthDate));
            },
              error => {
                this.isError = true;
                this.errorMessage = error.error.message;
              });
        });
    } else {
      /* this.user = this.visitingUser;
      this.userId = this.visitingUser.id;
      this.areRestrictedInformationShown = true; */
      this.authService.getCurrentUser().subscribe(
        data => {
          this.user = data;
          this.userId = data.id;
          console.log('current usr: ' + this.user);
          this.areRestrictedInformationShown = true;
          this.isVisitorOwner = true;
        },
        error => {
          this.isError = true;
          this.errorMessage = error.error.message;
        });
    }


  }

  getVisitingUser() {
    this.authService.getCurrentUser().subscribe(
      data => {
        this.visitingUser = data;
      },
      error => {
        this.isError = true;
        this.errorMessage = error.error.message;
      });
  }

  getVisitorRole() {
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
