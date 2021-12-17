import {Component, Inject} from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'app-remove-section-dialog',
  templateUrl: 'remove-section-dialog.component.html',
})
export class RemoveSectionDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<RemoveSectionDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {
      name: string;
    },
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}
