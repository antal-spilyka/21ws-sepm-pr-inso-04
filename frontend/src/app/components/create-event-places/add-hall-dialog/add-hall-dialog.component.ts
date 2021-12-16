import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-add-hall-dialog',
  templateUrl: './add-hall-dialog.component.html',
  styleUrls: ['./add-hall-dialog.component.scss']
})
export class AddHallDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: {id: number}, public dialogRef: MatDialogRef<AddHallDialogComponent>) { }

  ngOnInit(): void {
  }

  closeDialog() {
    this.dialogRef.close();
  }

}
