import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LocationPerformancesComponent } from './location-performances.component';

describe('LocationPerformancesComponent', () => {
  let component: LocationPerformancesComponent;
  let fixture: ComponentFixture<LocationPerformancesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LocationPerformancesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LocationPerformancesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
