import {Component, OnInit} from '@angular/core';
import {Performance} from '../../dtos/performance';
import {ActivatedRoute, Router} from '@angular/router';
import {PerformanceService} from '../../services/performance.service';
import {EventService} from '../../services/event.service';

@Component({
  selector: 'app-event-performances',
  templateUrl: './event-performances.component.html',
  styleUrls: ['./event-performances.component.scss']
})
export class EventPerformancesComponent implements OnInit {
  performancesList: Performance[] = [];
  error = false;
  errorMessage: string;

  constructor(private route: ActivatedRoute, private router: Router, private eventService: EventService) {
  }

  ngOnInit(): void {
    let id: number;
    this.route.paramMap.subscribe(paramMap => {
      id = Number(paramMap.get('id'));
    });
    this.eventService.findPerformancesByEvent(id).subscribe(
      {
        next: performances => {
          this.performancesList = performances;
          console.log(this.performancesList);
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
