import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PerformanceDetailedComponent } from './performance-detailed.component';

describe('PerformanceDetailedComponent', () => {
  let component: PerformanceDetailedComponent;
  let fixture: ComponentFixture<PerformanceDetailedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PerformanceDetailedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PerformanceDetailedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
