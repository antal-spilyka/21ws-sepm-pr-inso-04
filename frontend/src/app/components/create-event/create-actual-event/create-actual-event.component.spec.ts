import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateActualEventComponent } from './create-actual-event.component';

describe('CreateActualEventComponent', () => {
  let component: CreateActualEventComponent;
  let fixture: ComponentFixture<CreateActualEventComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateActualEventComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateActualEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
