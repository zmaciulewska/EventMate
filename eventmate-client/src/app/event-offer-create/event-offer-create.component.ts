import { Component, OnInit } from '@angular/core';
import { EventOfferForm } from '../domain/event-offer-form';
import { EventService } from '../services/event.service';
import { ActivatedRoute, Router } from '../../../node_modules/@angular/router';
import { CustomFormsModule } from 'ng2-validation';


@Component({
  selector: 'app-event-offer-create',
  templateUrl: './event-offer-create.component.html',
  styleUrls: ['./event-offer-create.component.css']
})
export class EventOfferCreateComponent implements OnInit {

  eventId: number;
  eventOfferForm = new EventOfferForm();
  tmpPrefferedDate: Date;
  maleCheckbox: boolean;
  femaleCheckbox: boolean;

  isSaveFailed;
  errorMessage;


  constructor(private eventService: EventService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  setupForm() {
    this.tmpPrefferedDate = new Date();
    this.maleCheckbox = false;
    this.femaleCheckbox = false;
    this.eventOfferForm.prefferedLocalization = '';
    this.eventOfferForm.prefferedMinAge = 18;
    this.eventOfferForm.prefferedMaxAge = 18;

    this.isSaveFailed = false;
    this.errorMessage = '';
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.eventId = params['id'];
    });
    this.setupForm();
  }

  reloadPage() {
    window.location.reload();
  }

  save() {
    this.eventService.addEventOffer(this.eventId, this.eventOfferForm)
      .subscribe(data => {
        console.log(data);
        this.setupForm();
        this.reloadPage();
      },
        error => {
          console.log(error);
          this.isSaveFailed = true;
          this.errorMessage = error.error.message;
        });
  }

  submit() {
    if (this.tmpPrefferedDate.toString().indexOf('-') !== -1) {
      this.eventOfferForm.prefferedDate = this.tmpPrefferedDate + ':00.000';
    } else {
      this.eventOfferForm.prefferedDate = this.tmpPrefferedDate.toISOString();
      console.log('nie zawiera - ');
    }

    if ((this.maleCheckbox && this.femaleCheckbox)
      || (!this.maleCheckbox && !this.femaleCheckbox)) {
      this.eventOfferForm.prefferedGender = 'BOTH';
    } else {
      if (this.maleCheckbox) {
        this.eventOfferForm.prefferedGender = 'MALE';
      } else {
        this.eventOfferForm.prefferedGender = 'FEMALE';
      }
    }

    this.save();
  }

}
