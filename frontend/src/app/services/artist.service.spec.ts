import {TestBed} from '@angular/core/testing';
import {ArtistService} from './artist.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {ReactiveFormsModule} from '@angular/forms';

describe('ArtistService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule, RouterTestingModule, ReactiveFormsModule],
  }));

  it('should be created', () => {
    const service: ArtistService = TestBed.inject(ArtistService);
    expect(service).toBeTruthy();
  });
});
