import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookPerformanceComponent } from './book-performance.component';

describe('BookPerformanceComponent', () => {
  let component: BookPerformanceComponent;
  let fixture: ComponentFixture<BookPerformanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BookPerformanceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BookPerformanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
