import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentInformationPickComponent } from './payment-information-pick.component';

describe('PaymentInformationPickComponent', () => {
  let component: PaymentInformationPickComponent;
  let fixture: ComponentFixture<PaymentInformationPickComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PaymentInformationPickComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PaymentInformationPickComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
