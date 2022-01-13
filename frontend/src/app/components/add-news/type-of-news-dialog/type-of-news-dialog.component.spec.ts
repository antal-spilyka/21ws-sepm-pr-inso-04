import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TypeOfNewsDialogComponent } from './type-of-news-dialog.component';

describe('TypeOfNewsDialogComponent', () => {
  let component: TypeOfNewsDialogComponent;
  let fixture: ComponentFixture<TypeOfNewsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TypeOfNewsDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TypeOfNewsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
