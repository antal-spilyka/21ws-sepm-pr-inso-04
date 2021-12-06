import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {debounceTime, distinctUntilChanged, Observable, switchMap} from 'rxjs';
import {EventPlace} from 'src/app/dtos/eventPlace';
import {EventPlaceService} from 'src/app/services/event-place.service';
import {Address} from 'src/app/dtos/address';
import {EventDto} from '../../../dtos/eventDto';
import {EventService} from '../../../services/event.service';

@Component({
  selector: 'app-create-event-place',
  templateUrl: './create-event-place.component.html',
  styleUrls: ['./create-event-place.component.scss']
})
export class CreateEventPlaceComponent implements OnInit {

  @Input() handleNext: (values: any) => void;
  @Input() setErrorFlag: (message?: string) => void;
  eventPlaces: Observable<EventPlace[]>;
  selectedEventPlace: EventPlace;
  selectedEvent: EventDto;
  now = new Date().toISOString().split(':');
  form = this.formBuilder.group({
    country: [{value: null, disabled: true}, Validators.required],
    state: [{value: null, disabled: true}, Validators.required],
    city: [{value: null, disabled: true}, Validators.required],
    zip: [{value: null, disabled: true}, Validators.required],
    street: [{value: null, disabled: true}, Validators.required],
    eventPlaceName: [null, Validators.required],
    name: [null, Validators.required],
    startTime: [this.now[0] + ':' + this.now[1], Validators.required],
    description: [null, Validators.required],
  });
  isNewEventPlace = false;

  constructor(
    private formBuilder: FormBuilder,
    private eventService: EventService,
    private eventPlaceService: EventPlaceService,
  ) {
    this.eventPlaces = this.form.get('eventPlaceName').valueChanges.pipe(
      distinctUntilChanged(),
      debounceTime(500),
      switchMap(name => !this.isNewEventPlace ?
        this.eventPlaceService.findEventPlace(name) :
        new Observable<EventPlace[]>()
      )
    );
  }

  onSelectEventPlace(eventPlace: EventPlace) {
    this.selectedEventPlace = eventPlace;
    console.log(eventPlace);
    const {name} = eventPlace;
    const {country, city, state, zip, street} = eventPlace.addressDto;
    this.form.controls.eventPlaceName.setValue(name);
    this.form.controls.country.setValue(country);
    this.form.controls.city.setValue(city);
    this.form.controls.state.setValue(state);
    this.form.controls.zip.setValue(zip);
    this.form.controls.street.setValue(street);
  }

  handleNewEventPlace() {
    this.isNewEventPlace = !this.isNewEventPlace;
    if (!this.isNewEventPlace) {
      this.form.controls.country.disable();
      this.form.controls.city.disable();
      this.form.controls.state.disable();
      this.form.controls.zip.disable();
      this.form.controls.street.disable();
    } else {
      this.form.controls.country.enable();
      this.form.controls.city.enable();
      this.form.controls.state.enable();
      this.form.controls.zip.enable();
      this.form.controls.street.enable();
      this.selectedEventPlace = null;
    }
  }

  async nextStep() {
    console.log(this.form.valid);
    if (!this.form.valid) {
      return;
    }
    console.log(this.isNewEventPlace, this.selectedEventPlace);
    await this.submitChanges();
  }

  async submitChanges() {
    const event = new EventDto();
    event.name = this.form.value.name;
    event.startTime = this.form.value.startTime;
    event.duration = 0;
    event.performances = [];
    let eventPlace;
    console.log(this.isNewEventPlace);
    if (this.isNewEventPlace) {
      eventPlace = new EventPlace();
      const address = new Address();
      address.city = this.form.value.city;
      address.country = this.form.value.country;
      address.state = this.form.value.state;
      address.zip = this.form.value.zip;
      address.street = this.form.value.street;
      eventPlace.name = this.form.value.eventPlaceName;
      eventPlace.addressDto = address;
      this.selectedEventPlace = eventPlace;
      console.log('1'+this.selectedEventPlace);
    } else {
      if (!this.selectedEventPlace) {
        this.setErrorFlag('Choose an eventPlace for your event');
        return;
      }
    }
    console.log('2'+this.selectedEventPlace);
    event.eventPlace = this.selectedEventPlace;
    event.description = this.form.value.description;
    console.log(event);
    this.eventPlaceService.createEventPlace(event.eventPlace).subscribe({
      next: async next => {
        console.log(next);
        this.selectedEventPlace = next;
        this.selectedEvent.eventPlace = next;
      }
    });
    /*this.eventService.saveEvent(event).subscribe({
      next: async next => {
        console.log(next);
        this.selectedEvent = next;
      },
      error: error => {
        if(error.status === 409) {
          this.setErrorFlag('An event with the same name already exists.');
        } else {
          this.setErrorFlag();
        }
      },
      complete: () => {
        this.handleNext(this.selectedEvent);
      }
    });*/
    this.selectedEvent = event;
    this.handleNext(this.selectedEvent);
  }

  ngOnInit(): void {
  }

}
