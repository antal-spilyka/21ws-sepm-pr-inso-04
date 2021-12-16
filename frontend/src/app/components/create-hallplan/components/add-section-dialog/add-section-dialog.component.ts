import {Component, Inject} from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {AddSectionDialogData} from '../../types';

@Component({
  selector: 'app-add-section-dialog',
  templateUrl: 'add-section-dialog.component.html',
})
export class AddSectionDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<AddSectionDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: AddSectionDialogData,
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}
