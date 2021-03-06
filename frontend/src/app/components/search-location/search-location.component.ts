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
  pageCounter = 0;
  eventLocations: EventPlace[] = [];
  error = false;
  errorMessage: string;
  locationAddresses: Map<number, Address> = new Map<number, Address>();

  constructor(private eventLocationService: EventLocationService, private router: Router) {
  }

  ngOnInit(): void {
  }

  onSubmit(newSearch = true) {
    if (newSearch) {
      this.eventLocations = [];
      this.locationAddresses.clear();
      this.pageCounter = 0;
    }
    if (this.detailedSearch === true) {
      this.loadDetailed();
    } else {
      this.loadGeneral();
    }
  }

  loadGeneral() {
    this.eventLocationService.findGeneralEventLocation(this.searchLocation, this.pageCounter).subscribe(
      {
        next: eventLocations => {
          this.submitted = true;
          this.eventLocations = this.eventLocations.concat(eventLocations);
          console.log(this.eventLocations);
          this.loadAddresses();
        }, error: error => this.handleError(error)
      }
    );
  }
  loadAddresses(){
    for (const i of this.eventLocations) {
      this.eventLocationService.getAddress(i.id).subscribe(
        {
          next: address => {
            console.log(address.city);
            this.locationAddresses[i.id] = address;
          }, error: error => this.handleError(error)
        }
      );
    }
  }

  loadDetailed() {
    this.eventLocationService.findEventLocation(this.searchAddress, this.pageCounter).subscribe(
      {
        next: eventLocations => {
          this.submitted = true;
          console.log(this.eventLocations);
          this.eventLocations = this.eventLocations.concat(eventLocations);
          console.log(this.eventLocations);
          this.loadAddresses();
        }, error: error => this.handleError(error)
      }
    );
  }

  loadPerformances(eventLocation: Address) {
    console.log(eventLocation);
    if (eventLocation.id) {
      this.router.navigateByUrl(`/locations/${eventLocation.id}/performances`);
    }
  }

  changeDetailed(){
    this.detailedSearch = !this.detailedSearch;
  }
  moreItems(){
    this.pageCounter = this.pageCounter+1;
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
