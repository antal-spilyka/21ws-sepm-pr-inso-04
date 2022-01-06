import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AddHallDialogComponent } from 'src/app/components/create-event-places/add-hall-dialog/add-hall-dialog.component';
import { Artist } from 'src/app/dtos/artist';

@Component({
  selector: 'app-add-artist-dialog',
  templateUrl: './add-artist-dialog.component.html',
  styleUrls: ['./add-artist-dialog.component.scss']
})
export class AddArtistDialogComponent implements OnInit {

  form = this.formBuilder.group({
    artistName: [null, Validators.required],
    artistDescription: [null, Validators.required]
  });

  constructor(
    public dialogRef: MatDialogRef<AddHallDialogComponent>,
    private formBuilder: FormBuilder
    ) { }

  ngOnInit(): void {
  }

  add() {
    if(this.form.invalid) {
      console.log('invalid');
      return;
    }
    this.dialogRef.close({
      bandName: this.form.value.artistName,
      description: this.form.value.artistDescription
    } as Artist);
  }
}
