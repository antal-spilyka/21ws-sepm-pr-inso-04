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
  ) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  getErrorMessage() {
    if (!this.data.onlyPriceEditable && this.data.sectors.find((s, i) => i !== this.data.editIndex && s.name === this.data.name)) {
      return 'Name is already used';
    } else if (!this.data.onlyPriceEditable && this.data.sectors.find((s, i) =>
      i !== 1 && i !== this.data.editIndex && s.color === this.data.color)) {
      return 'Color is already used';
    }
    return '';
  }

  shouldBeDisabled(): boolean {
    return this.data.name.length === 0 || this.data.price === 0.0 || this.data.price <= 0.0 ||
      (!this.data.onlyPriceEditable && !!this.data.sectors.find((s, i) => i !== this.data.editIndex && s.name === this.data.name)) ||
      (!this.data.onlyPriceEditable && !!this.data.sectors.find((s, i) =>
        i !== 1 && i !== this.data.editIndex && s.color === this.data.color));
  }
}
