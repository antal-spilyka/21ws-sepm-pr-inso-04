import { Component, OnInit } from '@angular/core';
import {Address} from '../../dtos/address';
import {EventLocationService} from '../../services/event-location.service';
import {EventDto} from '../../dtos/eventDto';
import {Router} from '@angular/router';

@Component({
  selector: 'app-search-location',
  templateUrl: './search-location.component.html',
  styleUrls: ['./search-location.component.scss']
})
export class SearchLocationComponent implements OnInit {
  searchAddress: Address = {
    id: null, city: '', state: '', zip: '', country: '', street: '',
};
  submitted = false;
  eventLocations: Address[] = [];
  error = false;
  errorMessage: string;
  constructor(private eventLocationService: EventLocationService, private router: Router) { }

  ngOnInit(): void {
  }
  onSubmit() {
    this.eventLocationService.findEventLocation(this.searchAddress).subscribe(
      {
        next: eventLocations => {
          this.submitted = true;
          console.log(this.eventLocations);
          this.eventLocations = eventLocations;
          console.log(this.eventLocations);
        }, error: error => this.handleError(error)
      }
    );
  }
  loadPerformances(eventLocation: Address){
    if(eventLocation.id){
      this.router.navigateByUrl(`/locations/${eventLocation.id}/performances`);
    }
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
