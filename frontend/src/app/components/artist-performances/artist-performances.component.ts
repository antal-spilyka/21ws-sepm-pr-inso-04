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
  pageCounter = 0;
  id: number;
  error = false;
  errorMessage: string;
  constructor(private route: ActivatedRoute, private router: Router, private performanceService: PerformanceService) { }

  ngOnInit(): void {
      let id: number;
    this.route.paramMap.subscribe( paramMap => {
      id = Number(paramMap.get('id'));
      this.id = id;
      this.loadArtists();
    });
  }
  loadPerformance(performance: Performance){
    if(performance.id){
      this.router.navigate([`/performances/${performance.id}`]);
    }
  }
  loadArtists(){
    this.performanceService.findPerformancesByArtist(this.id, this.pageCounter).subscribe(
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
    this.loadArtists();
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
