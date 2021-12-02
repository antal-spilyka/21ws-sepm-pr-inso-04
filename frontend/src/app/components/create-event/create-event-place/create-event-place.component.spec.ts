import {ComponentFixture, TestBed, waitForAsync} from '@angular/core/testing';
import { CreateEventPlaceComponent } from './create-event-place.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {MatAutocompleteModule} from '@angular/material/autocomplete';

describe('CreateEventPlaceComponent', () => {
  let component: CreateEventPlaceComponent;
  let fixture: ComponentFixture<CreateEventPlaceComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule, ReactiveFormsModule, MatAutocompleteModule],
      declarations: [CreateEventPlaceComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEventPlaceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
