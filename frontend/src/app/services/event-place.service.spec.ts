import { TestBed } from '@angular/core/testing';

import { EventPlaceService } from './event-place.service';

describe('EventPlaceService', () => {
  let service: EventPlaceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventPlaceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
