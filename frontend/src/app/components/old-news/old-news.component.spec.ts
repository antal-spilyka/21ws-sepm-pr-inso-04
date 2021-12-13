import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OldNewsComponent } from './old-news.component';

describe('OldNewsComponent', () => {
  let component: OldNewsComponent;
  let fixture: ComponentFixture<OldNewsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OldNewsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OldNewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
