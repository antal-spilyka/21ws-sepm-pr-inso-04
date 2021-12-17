import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEventPlacesComponent } from './create-event-places.component';

describe('CreateEventPlaceComponent', () => {
  let component: CreateEventPlacesComponent;
  let fixture: ComponentFixture<CreateEventPlacesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateEventPlacesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEventPlacesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
