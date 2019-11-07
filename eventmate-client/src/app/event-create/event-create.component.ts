import { CategoryService } from './../services/category.service';
import { CostForm } from './../domain/cost-form';
import { EventService } from './../services/event.service';
import { EventForm } from './../domain/event-form';
import { Component, OnInit } from '@angular/core';
import { Category } from '../domain/category';

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
  // selectedCategories: Map<string, Array<any>>;

  settings = {
    bigBanner: true,
    timePicker: true,
    // dateFormat: 'yyyy-MM-dd HH:mm',
    defaultOpen: false,
    // format: 'medium',
    closeOnSelect: true,
  };

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
    // this.eventForm.costs = [];

    this.eventForm.title = '';
    this.eventForm.description = '';
    this.eventForm.localization = '';
    this.eventForm.startDate = new Date();
    this.eventForm.endDate = new Date();
    this.eventForm.common = false;
    this.eventForm.continous = false;
    this.eventForm.siteUrl = '';
    this.eventForm.costs = [];
    this.eventForm.categoryIds = [];

    this.costForm.name = '';
    this.costForm.description = '';
    this.costForm.price = null;
    this.costForm.currency = 'PLN';

    // this.selectedCategories = new Map<string, Array<any>>();
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

  onItemSelect(item: any) {
    console.log('item:  ' + item);
    console.log('selected : ' + this.selectedCategories);
  }

  onSelectAll(items: any) {
    console.log(items);
  }

  onItemDeSelect(item: any) {
    console.log('deselect: ' + item);
    console.log('selected : ' + this.selectedCategories);
  }

  submit() {
    console.log('method submit');
    console.log('selected: ' + this.selectedCategories);
    console.log(this.eventForm);
    this.eventForm.categoryIds = this.selectedCategories.map(item => item.id);
    this.submitted = true;
    this.save();
  }

  /*  onSubmit() {
     console.log(this.eventForm);
     this.submitted = true;
     this.save();
   } */

}
