import { TestBed } from '@angular/core/testing';

import { CostService } from './cost.service';

describe('CostService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CostService = TestBed.get(CostService);
    expect(service).toBeTruthy();
  });
});
