import { Component, OnInit } from '@angular/core';
import { EventForm } from '../domain/event-form';
import { CostForm } from '../domain/cost-form';
import { Category } from '../domain/category';
import { CategoryService } from '../services/category.service';
import { EventService } from '../services/event.service';
import { Event } from '../domain/event';
import { ActivatedRoute, Router } from '../../../node_modules/@angular/router';

@Component({
  selector: 'app-event-edit',
  templateUrl: './event-edit.component.html',
  styleUrls: ['./event-edit.component.css']
})
export class EventEditComponent implements OnInit {

  existingEvent: Event;
  eventForm: any = {};
  // eventForm: EventForm;
  costs: CostForm[];
  costForm: CostForm = new CostForm();
  categories: Category[];
  selectedCategories: Category[];
  tmpStartDateTime: Date;
  tmpEndDateTime: Date;

  isUpdateFailed = false;
  errorMessage = '';

  dropdownSettings = {
    singleSelection: false,
    idField: 'id',
    textField: 'name',
    enableCheckAll: false,
    unSelectAllText: 'Odznacz wszystkie',
    itemsShowLimit: 3,
    allowSearchFilter: true
  };



  constructor(private eventService: EventService,
    private categoryService: CategoryService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {
    this.categoryService.getAll().subscribe(data => this.categories = data);
    this.route
      .params
      .subscribe(params => {
        this.eventService
          .getOneById(params['id'])
          .subscribe(data => {
            this.existingEvent = data;
            console.log(this.existingEvent);

            this.eventForm = data;

            /* this.eventForm = new EventForm();
            this.eventForm.title = this.existingEvent.title;
            this.eventForm.description = this.existingEvent.description;
            this.eventForm.localization = this.existingEvent.localization;
            this.eventForm.siteUrl = this.existingEvent.siteUrl;
            this.eventForm */

            this.tmpStartDateTime = new Date(this.existingEvent.startDate);
            this.tmpEndDateTime = new Date(this.existingEvent.endDate);
            this.selectedCategories = this.existingEvent.categoriess;
          });
      });
  }


  addCost() {
    this.costForm.currency = 'PLN';
    this.eventForm.costs.push(this.costForm);
    this.costForm = new CostForm();

  }

  removeCost(costForm: CostForm) {
    this.eventForm.costs = this.eventForm.costs.filter(item => item !== costForm);
  }

  submit() {
    // console.log('method submit');
    if (this.tmpStartDateTime.toString().indexOf('-') !== -1) {
      this.eventForm.startDate = this.tmpStartDateTime + ':00.000';
      // console.log(' zawiera - ');
    } else {
      this.eventForm.startDate = this.tmpStartDateTime.toISOString();
      // console.log('nie zawiera - ');
    }
    if (this.tmpEndDateTime.toString().indexOf('-') !== -1) {
      this.eventForm.endDate = this.tmpEndDateTime + ':00.000';
      // console.log('nie zawiera - ');
    } else {
      this.eventForm.endDate = this.tmpEndDateTime.toISOString();
      // console.log('nie zawiera - ');
    }

    this.eventForm.categoryIds = this.selectedCategories.map(item => item.id);
    // this.submitted = true;
    this.save();
  }

  save() {
    this.eventService.update(this.existingEvent.id, this.eventForm)
      .subscribe(data => {
        console.log(data);
        this.router.navigate(['/events/details/', this.existingEvent.id]);
      }, error => {
        console.log(error);
        this.isUpdateFailed = true;
        this.errorMessage = error.error.message;
      });

    // this.eventForm = new EventForm();
  }
}
