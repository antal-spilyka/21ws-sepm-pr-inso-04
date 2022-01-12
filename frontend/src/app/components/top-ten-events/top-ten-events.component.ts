import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {EventDto} from "../../dtos/eventDto";
import {EventService} from "../../services/event.service";
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {TopTenEvents} from "../../dtos/topTenEvents";
import {Order} from "../../dtos/order";
import {PaymentInformationPickComponent} from "../payment-information-pick/payment-information-pick.component";
import {SetOrderToBoughtDto} from "../../dtos/setOrderToBoughtDto";

@Component({
  selector: 'app-top-ten-events',
  templateUrl: './top-ten-events.component.html',
  styleUrls: ['./top-ten-events.component.scss']
})
export class TopTenEventsComponent implements OnInit {
  error = false;
  errorMessage = '';
  events: Observable<EventDto[]>;
  topTenEvents: TopTenEvents[];
  chosenEvent: EventDto;
  columns: string[] = ['Event name', 'Date', 'Duration', 'Place'];
  categories: string[];
  chosenCategory = new FormControl('', [Validators.required]);

  constructor(
    private eventService: EventService,
) { }

  ngOnInit(): void {
    this.getAllCategories();
  }

  getAllCategories(): void {
    this.eventService.getAllCategories().subscribe((categories) => {
      this.categories = categories;
      this.getTopTenEvents(this.categories[0]);
    });
  }

  getTopTenEvents(category = ''): void {
    category = category === '' ? this.chosenCategory.value : category;
    this.chosenCategory.setValue(category);
    this.eventService.getTopTenEvents(category).subscribe((topTenEvents) => {
      console.log(`getting top ten events ${topTenEvents.toString()}`);
      this.topTenEvents = topTenEvents;
    });
  }

  setChosenEvent(event: EventDto) {
    this.chosenEvent = event;
  }



  // region error handling

  vanishError() {
    this.error = false;
    this.errorMessage = '';
  }

  setErrorFlag(message?: string) {
    this.errorMessage = message ? message : 'Unknown Error';
    this.error = true;
  }

  // endregion
}

