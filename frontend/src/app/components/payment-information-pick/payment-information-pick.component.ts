import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {PaymentInformation} from '../../dtos/paymentInformation';
import {DialogData} from '../orders/orders.component';

@Component({
  selector: 'app-payment-information-pick',
  templateUrl: './payment-information-pick.component.html',
  styleUrls: ['./payment-information-pick.component.scss']
})
export class PaymentInformationPickComponent implements OnInit {
  displayedColumns: string[] = ['name', 'number', 'expirationDate', 'cvv'];

  constructor(public dialogRef: MatDialogRef<PaymentInformationPickComponent>,
              @Inject(MAT_DIALOG_DATA) public dialogData: DialogData) {
  }

  ngOnInit(): void {
  }

  choose(paymentInformation: PaymentInformation) {
    this.dialogRef.close(paymentInformation.id);
  }

}
