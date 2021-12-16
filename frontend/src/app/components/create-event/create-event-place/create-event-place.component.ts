import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {debounceTime, distinctUntilChanged, Observable, switchMap} from 'rxjs';
import {EventPlace} from 'src/app/dtos/eventPlace';
import {EventPlaceService} from 'src/app/services/event-place.service';
import {Address} from 'src/app/dtos/address';
import {EventDto} from '../../../dtos/eventDto';
import { EventService } from 'src/app/services/event.service';

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
    category: [null, Validators.required],
  });
  isNewEventPlace = false;

  constructor(
    private formBuilder: FormBuilder,
    private eventPlaceService: EventPlaceService,
    private eventService: EventService
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
    console.log(eventPlace);
    this.selectedEventPlace = eventPlace;
    console.log(eventPlace);
    const {name} = eventPlace;
    this.form.controls.eventPlaceName.setValue(name);
  }

  async nextStep() {
    if (!this.form.valid) {
      this.setErrorFlag('Please fill out the form.');
      return;
    }
    if(!this.selectedEventPlace || this.selectedEventPlace.name !== this.form.value.eventPlaceName) {
      this.setErrorFlag('Please select an Event Place from the Dropdown Menu.');
      return;
    }
    if(new Date() > new Date(this.form.value.startTime)) {
      this.setErrorFlag('The start time must be in the future');
      return;
    }
    await this.submitChanges();
  }

  async submitChanges() {
    const event = new EventDto();
    event.name = this.form.value.name;
    event.startTime = this.form.value.startTime;
    event.duration = 0;
    event.performances = [];
    event.eventPlace = this.selectedEventPlace;
    event.description = this.form.value.description;
    event.category = this.form.value.category;
    console.log(event);
    this.eventService.saveEvent(event).subscribe({
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
    });
  }

  ngOnInit(): void {
  }

}
