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
  pageCounter = 0;
  eventList: EventDto[] = [];
  error = false;
  errorMessage: string;

  constructor(private eventService: EventService, private router: Router) {
  }

  ngOnInit(): void {
  }
  onSubmit(newSearch = true) {
    if (this.searchEvent.duration < 0) {
      window.alert('Duration cannot be smaller than 0!');
    }
    if (newSearch) {
      this.eventList = [];
      this.pageCounter = 0;
    }
    if (this.detailedSearch === true) {
      this.loadDetailed();
    } else {
      this.loadGeneral();
    }
  }

  loadGeneral() {
    this.eventService.findGeneralEvent(this.generalSearchEvent, this.pageCounter).subscribe(
      {
        next: events => {
          this.submitted = true;
          this.eventList = this.eventList.concat(events);
          console.log(this.eventList);
        }, error: error => this.handleError(error)
      }
    );
  }

  loadDetailed() {
    this.eventService.findEvent(this.searchEvent, this.pageCounter).subscribe(
      {
        next: events => {
          this.submitted = true;
          console.log(this.eventList);
          this.eventList = this.eventList.concat(events);
          console.log(this.eventList);
        }, error: error => this.handleError(error)
      }
    );
  }

  loadPerformances(event: EventDto) {
    if (event.id) {
      this.router.navigateByUrl(`/events/${event.id}/performances`);
    }
  }

  changeDetailed() {
    this.detailedSearch = !this.detailedSearch;
  }

  moreItems() {
    this.pageCounter = this.pageCounter + 1;
    this.onSubmit(false);
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
