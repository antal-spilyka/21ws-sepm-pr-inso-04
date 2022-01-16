import {Component, OnInit} from '@angular/core';
import {Performance} from '../../dtos/performance';
import {ActivatedRoute, Router} from '@angular/router';
import {EventService} from '../../services/event.service';

@Component({
  selector: 'app-location-performances',
  templateUrl: './location-performances.component.html',
  styleUrls: ['./location-performances.component.scss']
})
export class LocationPerformancesComponent implements OnInit {
  performancesList: Performance[] = [];
  pageCounter = 0;
  error = false;
  errorMessage: string;
  id: number;

  constructor(private route: ActivatedRoute, private router: Router, private eventService: EventService) {
  }

  ngOnInit(): void {
    let id: number;
    this.route.paramMap.subscribe(paramMap => {
      id = Number(paramMap.get('id'));
      this.id = id;
      this.loadLocations();
    });
  }
  loadPerformance(performance: Performance){
    if(performance.id){
      this.router.navigate([`/performances/${performance.id}`]);
    }
  }
  loadLocations(){
    this.eventService.findPerformancesByLocation(this.id, this.pageCounter).subscribe(
      {
        next: performances => {
          this.performancesList = this.performancesList.concat(performances);
          console.log(this.performancesList);
        }, error: error => this.handleError(error)
      }
    );
  }
  moreItems(){
    this.pageCounter = this.pageCounter+1;
    this.loadLocations();
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
