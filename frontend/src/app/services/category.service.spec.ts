import { TestBed } from '@angular/core/testing';
import { CategoryService } from './category.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {ReactiveFormsModule} from '@angular/forms';

describe('CategoryService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule, RouterTestingModule, ReactiveFormsModule],
  }));

  it('should be created', () => {
    const service: CategoryService = TestBed.inject(CategoryService);
    expect(service).toBeTruthy();
  });
});
