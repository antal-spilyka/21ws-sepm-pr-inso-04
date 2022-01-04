import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {PerformanceService} from '../../services/performance.service';
import {PerformanceDetail} from '../../dtos/performance-detail';

@Component({
  selector: 'app-performance-detailed',
  templateUrl: './performance-detailed.component.html',
  styleUrls: ['./performance-detailed.component.scss']
})
export class PerformanceDetailedComponent implements OnInit {
  performance: PerformanceDetail = {
    artist: undefined,
    duration: 0,
    eventDto: undefined,
    hall: undefined,
    id: 0,
    name: '',
    startTime: undefined,
    tickets: undefined
  };
  error = false;
  errorMessage = '';

  constructor(private activatedRoute: ActivatedRoute,
              private router: Router, private performanceService: PerformanceService) {
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.performanceService.getPerformanceById(params['id']).subscribe(
        (performance: PerformanceDetail) => {
          this.performance = performance;
          this.performance.tickets = performance.tickets.map(ticket => ({
            ...ticket,
            seatIndex: ticket.seatIndex != null ? ticket.seatIndex + 1 : undefined,
            rowIndex: ticket.rowIndex != null ? ticket.rowIndex + 1 : undefined,
          }));
        },
        error => {
          this.defaultServiceErrorHandling(error);
        }
      );
    });
  }

  buy() {
    this.router.navigate(['/performances/book'], { state: {performance: this.performance }});
  }

  defaultServiceErrorHandling(error: any) {
    console.log(error);
    this.error = true;
    if (error.status === 0) {
      // If status is 0, the backend is probably down
      this.errorMessage = 'The backend seems not to be reachable';
    } else if (error.error.message === 'No message available') {
      // If no detailed error message is provided, fall back to the simple error name
      this.errorMessage = error.error.error;
    } else {
      this.errorMessage = error.error.message;
    }
  }
}
