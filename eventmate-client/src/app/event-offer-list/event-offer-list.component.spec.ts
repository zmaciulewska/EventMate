import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventOfferListComponent } from './event-offer-list.component';

describe('EventOfferListComponent', () => {
  let component: EventOfferListComponent;
  let fixture: ComponentFixture<EventOfferListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventOfferListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventOfferListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
