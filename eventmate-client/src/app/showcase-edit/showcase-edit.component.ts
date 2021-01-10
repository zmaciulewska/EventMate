import { Component, OnInit, Input } from '@angular/core';
import { UserService } from '../services/user.service';
import { Showcase } from '../domain/showcase';
import { ActivatedRoute, Router } from '../../../node_modules/@angular/router';
import { User } from '../domain/user';
import { UserDetailsComponent } from '../user-details/user-details.component';

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

  @Input() userId: number;


  constructor(private userService: UserService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {
    // this.route
    // .params
    // .subscribe(params => {
    this.userService
      .getOneById(this.userId)
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
    // });
  }

  submit() {
    if (this.tmpBirthDate.toString().indexOf('-') !== -1) {
      this.showcaseForm.birthDate = this.tmpBirthDate + ':00.000';
    } else {
      this.showcaseForm.birthDate = this.tmpBirthDate.toISOString();
    }
    this.showcaseForm.userId = this.existingUser.id;
    this.save();
  }

  save() {
    console.log(this.showcaseForm);
    this.userService.update(this.existingUser.id, this.showcaseForm)
      .subscribe(data => {
        console.log(data);
        location.reload();
      }, error => {
        console.log(error);
        this.errorMessage = error.error.message;
      });
  }

}
