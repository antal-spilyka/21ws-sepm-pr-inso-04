import {ComponentFixture, TestBed, waitForAsync} from '@angular/core/testing';
import { CreateActualEventComponent } from './create-actual-event.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {MatAutocompleteModule} from '@angular/material/autocomplete';

describe('CreateActualEventComponent', () => {
  let component: CreateActualEventComponent;
  let fixture: ComponentFixture<CreateActualEventComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule, ReactiveFormsModule, MatAutocompleteModule],
      declarations: [CreateActualEventComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateActualEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
