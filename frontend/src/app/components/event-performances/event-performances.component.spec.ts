import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventPerformancesComponent } from './event-performances.component';

describe('EventPerformancesComponent', () => {
  let component: EventPerformancesComponent;
  let fixture: ComponentFixture<EventPerformancesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EventPerformancesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EventPerformancesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
