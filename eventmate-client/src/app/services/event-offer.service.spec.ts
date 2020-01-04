import { TestBed } from '@angular/core/testing';

import { EventOfferService } from './event-offer.service';

describe('EventOfferService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EventOfferService = TestBed.get(EventOfferService);
    expect(service).toBeTruthy();
  });
});
