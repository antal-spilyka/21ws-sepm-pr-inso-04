import {TestBed} from '@angular/core/testing';
import {RoomService} from './room.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {ReactiveFormsModule} from '@angular/forms';

describe('RoomService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule, RouterTestingModule, ReactiveFormsModule],
  }));

  it('should be created', () => {
    const service: RoomService = TestBed.inject(RoomService);
    expect(service).toBeTruthy();
  });
});
