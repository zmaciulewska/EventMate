import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { Showcase } from '../domain/showcase';
import { ActivatedRoute, Router } from '../../../node_modules/@angular/router';
import { User } from '../domain/user';

@Component({
  selector: 'app-showcase-edit',
  templateUrl: './showcase-edit.component.html',
  styleUrls: ['./showcase-edit.component.css']
})
export class ShowcaseEditComponent implements OnInit {

  errorMessage: string;
  showcaseForm: any = {};
  existingUser: User;
  tmpBirthDate: Date;

  constructor(private userService: UserService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {
    this.route
      .params
      .subscribe(params => {
        this.userService
          .getOneById(params['id'])
          .subscribe(data => {

            if (data.showcase === null) {
              this.showcaseForm = new Showcase();
              this.tmpBirthDate = new Date();
            } else {
              this.showcaseForm = data.showcase;
              this.tmpBirthDate = new Date(data.showcase.birthDate);
            }
            this.existingUser = data;
            console.log(this.showcaseForm);
            console.log(this.existingUser);
          },
            error => {
              this.errorMessage = error.error.message;
            });
      });
  }

  submit() {
    // console.log('method submit');
    if (this.tmpBirthDate.toString().indexOf('-') !== -1) {
      this.showcaseForm.birthDate = this.tmpBirthDate + ':00.000';
      // console.log(' zawiera - ');
    } else {
      this.showcaseForm.birthDate = this.tmpBirthDate.toISOString();
      // console.log('nie zawiera - ');
    }
    this.showcaseForm.userId = this.existingUser.id;
    // this.existingUser.showcase = this.showcaseForm;
    this.save();
  }

  save() {
    console.log(this.showcaseForm);
    this.userService.update(this.existingUser.id, this.showcaseForm)
      .subscribe(data => {
        console.log(data);
        this.router.navigate(['/users/details/', this.existingUser.id]);
      }, error => {
        console.log(error);
        this.errorMessage = error.error.message;
      });

    // this.eventForm = new EventForm();
  }

}
