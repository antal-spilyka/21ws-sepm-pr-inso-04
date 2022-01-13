import { Component, OnInit } from '@angular/core';
import {Observable} from 'rxjs';
import {EventDto} from '../../dtos/eventDto';
import {EventService} from '../../services/event.service';
import {FormControl, Validators} from '@angular/forms';
import {TopTenEvents} from '../../dtos/topTenEvents';
import {Performance} from '../../dtos/performance';

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
  categories: string[];
  chosenCategory = new FormControl('', [Validators.required]);
  columns: string[] = ['performance', 'bandName', 'hallName', 'startTime', 'duration', 'goToPerformance'];

  eventsVisualized = [];
  view: any[] = [1290, 600];
  xAxis = true;
  yAxis = true;
  animations = true; // animations on load
  showGridLines = true; // grid lines
  showDataLabel = true; // numbers on bars
  gradient = true;
  colorScheme = {
    domain: ['#3f51b5']
  };
  schemeType = 'ordinal';
  barPadding = 5;
  roundEdges = true;

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
      console.log(topTenEvents);
      this.topTenEvents = topTenEvents;
      this.eventsVisualized = [];

      // initialize chart
      this.topTenEvents.forEach(topTenEvent => {
        const currentEvent = { name: topTenEvent.event.name, value: topTenEvent.ticketsSold };
        this.eventsVisualized.push(currentEvent);
      });
      console.log(this.eventsVisualized);
    });
  }

  setChosenEvent(currentTopTenEvent: any) {
    const foundEvent = this.topTenEvents.find(topTenEvent => topTenEvent.event.name === currentTopTenEvent.name);
    console.log(foundEvent);
    this.chosenEvent = foundEvent.event;
  }

  printEntity(performances: Performance[], entity: string): string {
    let outputString = '';
    if (entity === 'artist') {
      performances.forEach(performance => {
        outputString = outputString.concat(performance.artist.bandName).concat(', ');
      });
    } else if ('performance') {
      performances.forEach(performance => {
        outputString = outputString.concat(performance.name).concat(', ');
      });
    }
    return outputString.substring(0, outputString.length - 2);
  }

  printArtists(performances: Performance[]): string {
    let outputString = '';
    performances.forEach(performance => {
      outputString = outputString.concat(performance.artist.bandName).concat(', ');
    });
    return outputString.substring(0, outputString.length - 2);
  }

  onActivate(data: any): void {
    console.log('Activate', JSON.parse(JSON.stringify(data)));
  }

  formatString(input: string): string {
    return input.toUpperCase();
  }

  formatNumber(input: number): number {
    return input;
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

