import { CategoryService } from '../services/category.service';
import { CostForm } from '../domain/cost-form';
import { EventService } from '../services/event.service';
import { EventForm } from '../domain/event-form';
import { Component, OnInit } from '@angular/core';
import { Category } from '../domain/category';
import { Time } from '@angular/common';

@Component({
  selector: 'app-event-create',
  templateUrl: './event-create.component.html',
  styleUrls: ['./event-create.component.css']
})
export class EventCreateComponent implements OnInit {

  eventForm: EventForm = new EventForm();
  submitted = false;
  costs: CostForm[];
  costForm: CostForm = new CostForm();
  categories: Category[];
  selectedCategories: Category[];
  tmpStartDateTime: Date;
  tmpEndDateTime: Date;

  dropdownSettings = {
    singleSelection: false,
    idField: 'id',
    textField: 'name',
    enableCheckAll: false,
    // selectAllText: 'Select All',
    unSelectAllText: 'Unselect All',
    itemsShowLimit: 3,
    allowSearchFilter: true
  };
  constructor(private eventService: EventService, private categoryService: CategoryService) { }

  ngOnInit() {
    this.eventForm.title = '';
    this.eventForm.description = '';
    this.eventForm.localization = '';
    this.eventForm.startDate = '';
    this.eventForm.endDate = '';
    this.tmpStartDateTime = new Date();
    this.tmpEndDateTime = new Date();
    this.eventForm.common = false;
    this.eventForm.continous = false;
    this.eventForm.siteUrl = '';
    this.eventForm.costs = [];
    this.eventForm.categoryIds = [];
    this.costForm.name = '';
    this.costForm.description = '';
    this.costForm.price = null;
    this.costForm.currency = 'PLN';
    this.selectedCategories = [];
    this.categoryService.getAll().subscribe(data => this.categories = data);
  }

  newEvent(): void {
    this.submitted = false;
    this.eventForm = new EventForm();
  }

  save() {
    this.eventService.add(this.eventForm)
      .subscribe(data => console.log(data), error => console.log(error));
    this.eventForm = new EventForm();
  }

  addCost() {
    console.log('method add costt');
    this.costForm.currency = 'PLN';
    this.eventForm.costs.push(this.costForm);
    console.log(this.eventForm);
    this.costForm = new CostForm();

  }

  removeCost(costForm: CostForm) {
    console.log('method remove costt');
    this.eventForm.costs = this.eventForm.costs.filter(item => item !== costForm);

  }


  submit() {
    console.log('method submit');
    this.eventForm.startDate = this.tmpStartDateTime + ':00.000';
    this.eventForm.endDate = this.tmpEndDateTime + ':00.000';
    console.log('selected: ' + this.selectedCategories);
    console.log(this.eventForm);
    this.eventForm.categoryIds = this.selectedCategories.map(item => item.id);
    this.submitted = true;
    this.save();
  }

  info() {
    console.log('INFO:');
    console.log('Start Date: ' + this.tmpStartDateTime);
    console.log('iso Start Date: ' + this.tmpStartDateTime + ':00.000');

  }

  /*  onSubmit() {
     console.log(this.eventForm);
     this.submitted = true;
     this.save();
   } */

}
