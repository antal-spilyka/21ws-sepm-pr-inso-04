import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {PerformanceService} from '../../services/performance.service';
import {Performance} from '../../dtos/performance';
@Component({
  selector: 'app-artist-performances',
  templateUrl: './artist-performances.component.html',
  styleUrls: ['./artist-performances.component.scss']
})
export class ArtistPerformancesComponent implements OnInit {
  performancesList: Performance[] = [];
  error = false;
  errorMessage: string;
  constructor(private route: ActivatedRoute, private router: Router, private performanceService: PerformanceService) { }

  ngOnInit(): void {
      let id: number;
    this.route.paramMap.subscribe( paramMap => {
      id = Number(paramMap.get('id'));
    });
    this.performanceService.findPerformancesByArtist(id).subscribe(
      {
        next: performances => {
          this.performancesList = performances;
          console.log(this.performancesList);
        }, error: error => this.handleError(error)
      }
    );
  }
  loadPerformance(performance: Performance){
    if(performance.id){
      this.router.navigate([`/performances/${performance.id}`, JSON.stringify(performance)]);
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
