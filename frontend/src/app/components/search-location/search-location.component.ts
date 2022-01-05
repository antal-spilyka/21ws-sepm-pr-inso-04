import {Component, OnInit} from '@angular/core';
import {Address} from '../../dtos/address';
import {EventLocationService} from '../../services/event-location.service';
import {Router} from '@angular/router';
import {EventPlace} from '../../dtos/eventPlace';

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
  detailedSearch = false;
  searchLocation = '';
  eventLocations: EventPlace[] = [];
  error = false;
  errorMessage: string;
  locationAddresses: Map<number, Address> = new Map<number, Address>();

  constructor(private eventLocationService: EventLocationService, private router: Router) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    if (this.detailedSearch === true) {
      this.eventLocationService.findEventLocation(this.searchAddress).subscribe(
        {
          next: eventLocations => {
            this.submitted = true;
            this.eventLocations = eventLocations;
            console.log(this.eventLocations);
            for (const i of eventLocations) {
              this.eventLocationService.getAddress(i.id).subscribe(
                {
                  next: address => {
                    console.log(address.city);
                    this.locationAddresses[i.id] = address;
                  }, error: error => this.handleError(error)
                }
              );
            }
          }, error: error => this.handleError(error)
        }
      );
    } else {
      this.eventLocationService.findGeneralEventLocation(this.searchLocation).subscribe(
        {
          next: eventLocations => {
            this.submitted = true;
            this.eventLocations = eventLocations;
            console.log(this.eventLocations);
            for (const i of eventLocations) {
              this.eventLocationService.getAddress(i.id).subscribe(
                {
                  next: address => {
                    console.log(address.city);
                    this.locationAddresses[i.id] = address;
                  }, error: error => this.handleError(error)
                }
              );
            }
          }, error: error => this.handleError(error)
        }
      );
    }
  }

  loadPerformances(eventLocation: Address) {
    if (eventLocation.id) {
      this.router.navigateByUrl(`/locations/${eventLocation.id}/performances`);
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
