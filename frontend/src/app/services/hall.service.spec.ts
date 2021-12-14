import {TestBed} from '@angular/core/testing';
import {HallService} from './hall.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {ReactiveFormsModule} from '@angular/forms';

describe('RoomService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule, RouterTestingModule, ReactiveFormsModule],
  }));

  it('should be created', () => {
    const service: HallService = TestBed.inject(HallService);
    expect(service).toBeTruthy();
  });
});
