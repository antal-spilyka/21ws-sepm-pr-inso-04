import {Component, OnInit} from '@angular/core';
import {EventInquiry} from '../../dtos/eventInquiry';
import {EventService} from '../../services/event.service';
import {EventDto} from '../../dtos/eventDto';
import {EventSearchDto} from '../../dtos/eventSearchDto';

@Component({
  selector: 'app-search-events',
  templateUrl: './search-events.component.html',
  styleUrls: ['./search-events.component.scss']
})
export class SearchEventsComponent implements OnInit {
  searchEvent: EventSearchDto = {
    duration: null, content: '', categoryName: '', description: null,
  };
  eventList: EventDto[] = [];
  private error = false;
  private errorMessage: string;

  constructor(private eventService: EventService) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    if (this.searchEvent.duration < 0) {
      window.alert('Duration cannot be smaller than 0!');
    } else {
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
