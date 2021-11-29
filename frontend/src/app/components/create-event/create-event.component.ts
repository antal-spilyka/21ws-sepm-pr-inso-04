import { Component, OnInit } from '@angular/core';
import { Artist } from '../../dtos/artist';
import { Category } from '../../dtos/category';
import { EventPlace } from '../../dtos/eventPlace';
import { Room } from '../../dtos/room';
import { Step } from './state';

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.scss']
})
export class CreateEventComponent implements OnInit {

  step: Step = Step.eventPlace;

  eventPlace: EventPlace;
  room: Room;
  artist: Artist;
  category: Category;
  error = false;

  constructor() { }

  public get stepType(): typeof Step {
    return Step;
  }

  ngOnInit(): void {
  }

  setErrorFlag = () => {
    this.error = true;
  };

  vanishError() {
    this.error = false;
  }

  handleNext = (values: any) => {
    if(this.step === Step.eventPlace) {
      const { selectedEventPlace, selectedRoom } = values;
      this.eventPlace = selectedEventPlace;
      this.room = selectedRoom;
      this.step = Step.artist;
    } else if(this.step === Step.artist) {
      const { selectedArtist, selectedCategory } = values;
      this.artist = selectedArtist;
      this.category = selectedCategory;
      this.step = Step.event;
    }
  };

}
