import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEventPlaceComponent } from './create-event-place.component';

describe('CreateEventPlaceComponent', () => {
  let component: CreateEventPlaceComponent;
  let fixture: ComponentFixture<CreateEventPlaceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateEventPlaceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEventPlaceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
