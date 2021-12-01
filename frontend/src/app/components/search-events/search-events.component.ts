import { Component, OnInit } from '@angular/core';
import {EventInquiry} from '../../dtos/eventInquiry';
import {EventService} from '../../services/event.service';
import {EventDto} from '../../dtos/eventDto';

@Component({
  selector: 'app-search-events',
  templateUrl: './search-events.component.html',
  styleUrls: ['./search-events.component.scss']
})
export class SearchEventsComponent implements OnInit {
  searchEvent: EventInquiry = {
  name: '', duration: null, content: '', dateTime: null, categoryName: '', roomId: null,
  artistId: null, description: null,
};
  eventList: EventDto[] = [];
  private error = false;
  private errorMessage: string;
  constructor(private eventService: EventService) { }

  ngOnInit(): void {
  }

  onSubmit() {
    this.eventService.findEvent(this.searchEvent).subscribe(
      {
        next: events => {
          console.log(this.eventList);
          this.eventList = events;
          console.log(this.eventList);
        }, error: error => this.handleError(error)
      }
    );
  }

  private handleError(error: any) {
    console.log(error);
    this.error = true;
    if (error.status === 0) {
      this.errorMessage = 'Cannot reach the backend';
    } else if (error.error.message === 'No message available') {
      this.errorMessage = error.error.error;
    } else {
      this.errorMessage = error.error.message;
    }
  }
}
