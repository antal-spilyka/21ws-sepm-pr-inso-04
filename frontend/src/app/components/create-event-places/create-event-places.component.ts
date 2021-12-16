import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Address } from 'src/app/dtos/address';
import { EventPlace } from 'src/app/dtos/eventPlace';
import { EventPlaceService } from 'src/app/services/event-place.service';

@Component({
  selector: 'app-create-event-places',
  templateUrl: './create-event-places.component.html',
  styleUrls: ['./create-event-places.component.scss']
})
export class CreateEventPlacesComponent implements OnInit {

  error = false;
  errorMessage = '';

  form = this.formBuilder.group({
    name: [null, Validators.required],
    country: [null, Validators.required],
    state: [null, Validators.required],
    city: [null, Validators.required],
    zip: [null, Validators.required],
    street: [null, Validators.required]
  });

  constructor(private formBuilder: FormBuilder, private eventPlaceService: EventPlaceService) { }

  ngOnInit(): void {
  }

  submit() {
    if(this.form.invalid) {
      //do something
      return;
    }

    const addressDto = {
      city: this.form.value.city,
      state: this.form.value.state,
      zip: this.form.value.zip,
      country: this.form.value.country,
      street: this.form.value.street,
    } as Address;

    const eventPlace = {
      name: this.form.value.name,
      addressDto
    } as EventPlace;

    this.eventPlaceService.createEventPlace(eventPlace).subscribe({
      next: () => {
        history.back();
      },
      error: error => {
        if(error.status === 409) {
          this.setErrorFlag('An Event Place with the same name already exists.');
        } else {
          this.setErrorFlag();
        }
      }
    });
  }

  setErrorFlag(message?: string) {
    this.error = true;
    this.errorMessage = message ? message : 'Unknown error.';
  }

  vanishError() {
    this.error = false;
    this.errorMessage = '';
  }

}
