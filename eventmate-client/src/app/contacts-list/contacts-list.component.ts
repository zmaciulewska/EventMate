import { Contact } from './../domain/contact';
import { Component, OnInit, Inject, Input } from '@angular/core';
import { UserService } from '../services/user.service';
import { Router, ActivatedRoute } from '../../../node_modules/@angular/router';

@Component({
  selector: 'app-contacts-list',
  templateUrl: './contacts-list.component.html',
  styleUrls: ['./contacts-list.component.css']
})
export class ContactsListComponent implements OnInit {

  contacts: Contact[];
  emptyList = true;


  @Input() userId: number;

  constructor(private userService: UserService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit() {
    console.log(this.userId);
    if (this.userId !== null) {
      console.log('userid != null');
      this.userService
        .getUserContacts(this.userId)
        .subscribe(data => {
          this.contacts = data;
          if (this.contacts.length > 0) { this.emptyList = false; }
        });
    } else {
      console.log('userid == null');
      this.route
        .params
        .subscribe(params => {
          this.userId = params['id'];
          this.userService
            .getUserContacts(params['id'])
            .subscribe(data => {
              this.contacts = data;
              if (this.contacts.length > 0) { this.emptyList = false; }
            });
        });
    }
  }

}
