import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { debounceTime, distinctUntilChanged, Observable, switchMap } from 'rxjs';
import { EventPlace } from 'src/app/dtos/eventPlace';
import { EventPlaceService } from 'src/app/services/event-place.service';
import { Room } from 'src/app/dtos/room';
import { RoomService } from 'src/app/services/room.service';
import { RoomInquiry } from 'src/app/dtos/RoomInquiry';
import { Address } from 'src/app/dtos/address';

@Component({
  selector: 'app-create-event-place',
  templateUrl: './create-event-place.component.html',
  styleUrls: ['./create-event-place.component.scss']
})
export class CreateEventPlaceComponent implements OnInit {

  @Input() handleNext: (values: any) => void;
  @Input() setErrorFlag: () => void;

  eventPlaces: Observable<EventPlace[]>;
  rooms: Observable<Room[]>;
  selectedEventPlace: EventPlace;
  selectedRoom: Room;
  form = this.formBuilder.group({
    name: [null, Validators.required],
    country: [{value: null, disabled: true}, Validators.required],
    state: [{value: null, disabled: true}, Validators.required],
    city: [{value: null, disabled: true}, Validators.required],
    zip: [{value: null, disabled: true}, Validators.required],
    roomName: [null, Validators.required]
  });
  isNewEventPlace = false;
  isNewRoom = false;

  constructor(
      private formBuilder: FormBuilder,
      private eventPlaceService: EventPlaceService,
      private roomService: RoomService
    ) {
    this.eventPlaces = this.form.get('name').valueChanges.pipe(
        distinctUntilChanged(),
        debounceTime(500),
        switchMap(name => !this.isNewEventPlace ?
          this.eventPlaceService.findEventPlace(name) :
          new Observable<EventPlace[]>()
      )
    );
    this.rooms = this.form.get('roomName').valueChanges.pipe(
      distinctUntilChanged(),
      debounceTime(500),
      switchMap(name => this.selectedEventPlace && !this.isNewRoom ?
        this.roomService.findRoom({
        name,  eventPlaceName: this.selectedEventPlace.name
      }) :
      new Observable<Room[]>()
    )
    );
  }

  onSelectEventPlace(eventPlace: EventPlace) {
    this.selectedEventPlace = eventPlace;
    const {name} = eventPlace;
    const {country, city, state, zip} = eventPlace.addressDto;
    this.form.controls.name.setValue(name);
    this.form.controls.country.setValue(country);
    this.form.controls.city.setValue(city);
    this.form.controls.state.setValue(state);
    this.form.controls.zip.setValue(zip);
  }

  onSelectRoom(room: Room) {
    const { name } = room;
    this.selectedRoom = room;
    this.form.controls.roomName.setValue(name);
  }

  handleNewEventPlace() {
    this.isNewEventPlace = !this.isNewEventPlace;
    if(!this.isNewEventPlace) {
      this.form.controls.country.disable();
      this.form.controls.city.disable();
      this.form.controls.state.disable();
      this.form.controls.zip.disable();
    } else {
      if(this.form.controls.artist) {
        this.form.controls.artist.setValue(null);
      };
      this.form.controls.country.enable();
      this.form.controls.city.enable();
      this.form.controls.state.enable();
      this.form.controls.zip.enable();
      this.selectedEventPlace = null;
    }
  }

  handleNewRoom() {
    this.isNewRoom = !this.isNewRoom;
    if(this.isNewRoom) {
      this.selectedRoom = null;
      if(this.form.controls.roomName) {
        this.form.controls.roomName.setValue(null);
      }
      this.selectedRoom = null;
    }
  }

  async nextStep() {
    if(!this.form.valid) {
      return;
    }
    console.log(this.isNewRoom, this.isNewEventPlace, this.selectedEventPlace);
    if(this.isNewEventPlace && !(this.selectedRoom)) {
      console.log(1);
      await this.submitChanges();
    } else if(this.isNewRoom && this.selectedEventPlace) {
      console.log(2);
      await this.submitRoomChanges(this.selectedEventPlace.name);
    } else if(!this.isNewRoom && this.selectedEventPlace) {
      console.log(3);
      await this.submitRoomChanges(this.selectedEventPlace.name);
    } else {
      return;
    }
  }

  async submitChanges() {
    const eventPlace = new EventPlace();
    const address = new Address();
    address.city = this.form.value.city;
    address.country = this.form.value.country;
    address.state = this.form.value.state;
    address.zip = this.form.value.zip;
    eventPlace.name = this.form.value.name;
    eventPlace.addressDto = address;
    this.eventPlaceService.createEventPlace(eventPlace).subscribe({
      next: async next => {
        this.selectedEventPlace = next;
        await this.submitRoomChanges(next.name);
      },
      error: error => {
        this.setErrorFlag();
      }
    });
  }

  async submitRoomChanges(eventPlaceName: string) {
    const roomInquiry = new RoomInquiry();
    roomInquiry.eventPlaceName = eventPlaceName;
    roomInquiry.name = this.form.value.roomName;
    console.log(roomInquiry);
    this.roomService.createRoom(roomInquiry).subscribe({
      next: next => {
        this.selectedRoom = next;
      },
      error: error => {
        this.setErrorFlag();
      },
      complete: () => {
        this.handleNext({selectedEventPlace: this.selectedEventPlace, selectedRoom: this.selectedRoom});
      }
    });
  }

  ngOnInit(): void {
  }

}
