import {Component, OnInit} from '@angular/core';
import {Step} from './state';
import {EventDto} from '../../dtos/eventDto';

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.scss']
})
export class CreateEventComponent implements OnInit {
  step: Step = Step.event;
  error = false;
  errorMessage = '';
  selectedEvent: EventDto;

  constructor() {
  }

  public get stepType(): typeof Step {
    return Step;
  }

  ngOnInit(): void {
  }

  setErrorFlag = (message?: string) => {
    if (message) {
      this.errorMessage = message;
    } else {
      this.errorMessage = 'An unknown error occured.';
    }
    this.error = true;
  };

  vanishError() {
    this.error = false;
    this.errorMessage = '';
  }

  handleNext = (event: EventDto) => {
    console.log(event);
    if (this.step === Step.event) {
      this.selectedEvent = event;
      this.step = Step.performances;
    }
  };
}
