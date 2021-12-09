import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditPaymentInformationDialogComponent } from './edit-payment-information-dialog.component';

describe('EditPaymentInformationDialogComponent', () => {
  let component: EditPaymentInformationDialogComponent;
  let fixture: ComponentFixture<EditPaymentInformationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditPaymentInformationDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditPaymentInformationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
