import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {PaymentInformation} from '../../../dtos/paymentInformation';

@Component({
  selector: 'app-edit-payment-information-dialog',
  templateUrl: './edit-payment-information-dialog.component.html',
  styleUrls: ['./edit-payment-information-dialog.component.scss']
})
export class EditPaymentInformationDialogComponent implements OnInit {

  creditCardNumberControl = new FormControl('', [Validators.required,
    // eslint-disable-next-line max-len
    Validators.pattern(/^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\d{3})\d{11})$/im)]);

  creditCardNameControl = new FormControl('', [Validators.required]);
  creditCardExperationMonthControl = new FormControl('', [Validators.required,
    Validators.pattern(/^0[1-9]|1[0-2]$/im)]);
  creditCardExperationYearControl = new FormControl('', [Validators.required, Validators.pattern(/^19[5-9]\d|20[0-4]\d|2050$/im)]);
  creditCardCvvControl = new FormControl('', [Validators.required, Validators.pattern(/^[0-9]{3}$/im)]);

  errorMessage = '';
  error = false;

  constructor(public dialogRef: MatDialogRef<EditPaymentInformationDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public dialogData: PaymentInformation) { }

  ngOnInit(): void {
    this.creditCardNameControl.setValue(this.dialogData.creditCardName);
    this.creditCardCvvControl.setValue(this.dialogData.creditCardCvv);
    this.creditCardExperationYearControl.setValue(this.dialogData.creditCardExpirationDate.substring(2));
    this.creditCardExperationMonthControl.setValue(this.dialogData.creditCardExpirationDate.substring(0, 2));
    this.creditCardNumberControl.setValue(this.dialogData.creditCardNr);
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
    this.errorMessage = null;
  }

  getErrorMessage(control) {
    if (control.hasError('required')) {
      return 'You must enter a value';
    }
    if (control.hasError('minlength')) {
      return 'Not a valid length';
    }
    if (control.hasError('pattern')) {
      return 'Not a valid pattern';
    }
    return '';
  }

  save(): void {
    if (this.creditCardNumberControl.valid && this.creditCardExperationMonthControl.valid
    && this.creditCardExperationYearControl.valid && this.creditCardNameControl.valid
    && this.creditCardCvvControl.valid) {
      const paymentInformation = {
        creditCardName: this.creditCardNameControl.value,
        creditCardCvv: this.creditCardCvvControl.value, creditCardNr: this.creditCardNumberControl.value,
        creditCardExpirationDate: this.creditCardExperationMonthControl.value + this.creditCardExperationYearControl.value
      };
      this.dialogRef.close(paymentInformation);
    } else {
      console.log('Invalid input');
    }
  }

}
