import { Component, Input, OnInit, Output } from '@angular/core';
import { FormBuilder } from '@angular/forms';
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

  eventPlaces: Observable<EventPlace[]>;
  rooms: Observable<Room[]>;
  selectedEventPlace: EventPlace;
  selectedRoom: Room;
  form = this.formBuilder.group({
    name: [null],
    country: [{value: null, disabled: true}],
    state: [{value: null, disabled: true}],
    city: [{value: null, disabled: true}],
    zip: [{value: null, disabled: true}],
    roomName: [null]
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
      switchMap(name => this.selectedEventPlace || !this.isNewRoom ?
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
    }
  }

  async nextStep() {
    const selectedEventPlace = this.selectedEventPlace;
    const selectedRoom = this.selectedRoom;
    if(this.isNewEventPlace) {
      await this.submitChanges();
    } else if(this.isNewRoom && selectedEventPlace) {
      await this.submitRoomChanges(selectedEventPlace.name);
    }
    this.handleNext({selectedEventPlace, selectedRoom});
  }

  sleep(milliseconds) {
    return new Promise(resolve => setTimeout(resolve, milliseconds));
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
        console.log(next);
        this.sleep(2000);
        await this.submitRoomChanges(next.name);
      },
      error: error => {
        //  TODO: implement error
        console.log('place', error);
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
        //  TODO: implement error
        console.log('room', error);
      }
    });
  }

  ngOnInit(): void {
  }

}
