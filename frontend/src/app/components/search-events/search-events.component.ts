import {Component, OnInit} from '@angular/core';
import {EventService} from '../../services/event.service';
import {EventDto} from '../../dtos/eventDto';
import {EventSearchDto} from '../../dtos/eventSearchDto';
import {Router} from '@angular/router';

@Component({
  selector: 'app-search-events',
  templateUrl: './search-events.component.html',
  styleUrls: ['./search-events.component.scss']
})
export class SearchEventsComponent implements OnInit {
  searchEvent: EventSearchDto = {
    duration: null, description: '', category: '',
  };
  submitted = false;
  detailedSearch = false;
  generalSearchEvent = '';
  eventList: EventDto[] = [];
  error = false;
  errorMessage: string;

  constructor(private eventService: EventService, private router: Router) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    if (this.searchEvent.duration < 0) {
      window.alert('Duration cannot be smaller than 0!');
    } else {
      if (this.detailedSearch === true) {
        this.eventService.findEvent(this.searchEvent).subscribe(
          {
            next: events => {
              this.submitted = true;
              console.log(this.eventList);
              this.eventList = events;
              console.log(this.eventList);
            }, error: error => this.handleError(error)
          }
        );
      } else{
        this.eventService.findGeneralEvent(this.generalSearchEvent).subscribe(
          {
            next: events => {
              this.submitted = true;
              this.eventList = events;
              console.log(this.eventList);
            }, error: error => this.handleError(error)
          }
        );
      }
    }
  }

  loadPerformances(event: EventDto) {
    if (event.id) {
      this.router.navigateByUrl(`/events/${event.id}/performances`);
    }
  }

  changeDetailed(){
    this.detailedSearch = !this.detailedSearch;
  }

  vanishError(): void {
    this.errorMessage = null;
    this.error = false;
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
