import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventOfferCreateComponent } from './event-offer-create.component';

describe('EventOfferCreateComponent', () => {
  let component: EventOfferCreateComponent;
  let fixture: ComponentFixture<EventOfferCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventOfferCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventOfferCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
