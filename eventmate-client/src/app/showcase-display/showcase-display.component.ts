import { Component, OnInit, Input } from '@angular/core';
import { UserService } from '../services/user.service';
import { Router, ActivatedRoute } from '../../../node_modules/@angular/router';
import { DateFormatPipe } from '../utils/DateFormatPipe';
import { Showcase } from '../domain/showcase';

@Component({
  selector: 'app-showcase-display',
  templateUrl: './showcase-display.component.html',
  styleUrls: ['./showcase-display.component.css']
})
export class ShowcaseDisplayComponent implements OnInit {

  errorMessage: string;
  // birthDateString: string;
  gender: string;

  showcase: any;

  @Input()
  userId: number;

  constructor(private userService: UserService,
    private router: Router,
    private route: ActivatedRoute,
    private dateFormatPipe: DateFormatPipe) { }

  ngOnInit() {
    this.userService
      .getUserShowcase(this.userId)
      .subscribe(data => {
        this.showcase = data;

        if (this.showcase !== null) {
          if (this.showcase.gender === 'MALE') {
            this.gender = 'mężczyzna';
          } else {
            this.gender = 'kobieta';
          }
        }
      },
        error => {
          this.errorMessage = error.error.message;
        });
  }

}
