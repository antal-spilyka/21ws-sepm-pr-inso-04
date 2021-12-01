import { TestBed } from '@angular/core/testing';
import { EventPlaceService } from './event-place.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {ReactiveFormsModule} from '@angular/forms';

describe('EventPlaceService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule, RouterTestingModule, ReactiveFormsModule],
  }));

  it('should be created', () => {
    const service: EventPlaceService = TestBed.inject(EventPlaceService);
    expect(service).toBeTruthy();
  });
});
