import { element } from 'protractor';
import { EventService } from '../services/event.service';
import { Component, OnInit } from '@angular/core';
import { Event } from '../domain/event';
import { ActivatedRoute, Router } from '@angular/router';
import { DateFormatPipe } from '../utils/DateFormatPipe';
import { CostService } from '../services/cost.service';
import { Cost } from '../domain/cost';

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.css']
})
export class EventDetailsComponent implements OnInit {

  event: Event;
  startDateString: String;
  endDateString: String;
  costs: Cost[];

  constructor(private route: ActivatedRoute,
    private eventService: EventService,
    private dateFormatPipe: DateFormatPipe,
    private costService: CostService) { }

  ngOnInit() {
    this.route
      .params
      .subscribe(params => {
        this.eventService
          .getOneById(params['id'])
          .subscribe(data => {
            this.event = data;
            this.startDateString = this.dateFormatPipe.transform(new Date(this.event.startDate));
            this.endDateString = this.dateFormatPipe.transform(new Date(this.event.endDate));
            console.log(this.event);

            this.costs = [];
            this.event.costIds.forEach(costId => {
              console.log('costid: ' + costId);
              this.costService.getOneById(costId).subscribe(cost => {
                console.log('cost: ' + cost);
                this.costs.push(cost);
              });
            });

          });
      });


    console.log('2: ' + this.event);


  }

  deleteEvent(id: number) {
    this.eventService.delete(id).subscribe(res => console.log('Event deleted'));
  }


}
