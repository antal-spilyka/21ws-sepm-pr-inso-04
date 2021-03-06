import {Component, OnInit} from '@angular/core';
import {Hall} from '../../dtos/hall';
import {HallService} from '../../services/hall.service';
import {PerformanceSearchDto} from '../../dtos/performanceSearchDto';
import {PerformanceService} from '../../services/performance.service';
import {Performance} from '../../dtos/performance';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-search-time',
  templateUrl: './search-time.component.html',
  styleUrls: ['./search-time.component.scss']
})
export class SearchTimeComponent implements OnInit {
  performanceSearchDto: PerformanceSearchDto = {
    eventName: '', startTime: '', hallName: '', price: null,
  };
  performanceList: Performance[] = [];
  detailedSearch = false;
  generalSearchTime = '';
  roomsList: Hall[] = [];
  roomMap = new Map();
  submitted = false;
  pageCounter = 0;
  private error = false;
  private errorMessage: string;

  constructor(private performanceService: PerformanceService, private roomService: HallService,
              private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit(): void {
    //this.loadRooms();
  }

  loadRooms() {
    this.roomService.getAllRooms().subscribe(
      {
        next: rooms => {
          console.log(this.roomsList);
          this.roomsList = rooms;
          console.log(this.roomsList);
          if (this.roomsList !== []) {
            for (const room of this.roomsList) {
              this.roomMap.set(room.id, room.name);
            }
          }
        }, error: error => this.handleError(error)
      }
    );
  }
  onSubmit(newSearch = true) {
    if (newSearch) {
      this.performanceList = [];
      this.pageCounter = 0;
    }
    if (this.detailedSearch === true) {
      this.loadDetailed();
    } else {
      this.loadGeneral();
    }
  }

  loadDetailed() {
    this.performanceService.findPerformanceByDateTime(this.performanceSearchDto, this.pageCounter).subscribe(
      {
        next: performances => {
          this.submitted = true;
          console.log(this.performanceList);
          this.performanceList = this.performanceList.concat(performances);
          console.log(this.performanceList);
        }, error: error => this.handleError(error)
      }
    );
  }

  loadGeneral() {
    this.performanceService.findGeneralPerformanceByDateTime(this.generalSearchTime, this.pageCounter).subscribe(
      {
        next: performances => {
          this.submitted = true;
          this.performanceList = this.performanceList.concat(performances);
          console.log(this.performanceList);
        }, error: error => this.handleError(error)
      }
    );
  }

  loadPerformance(performance: Performance) {
    if (performance.id) {
      this.router.navigate([`/performances/${performance.id}`]);
    }
  }

  moreItems() {
    this.pageCounter = this.pageCounter + 1;
    this.onSubmit(false);
  }

  changeDetailed() {
    this.detailedSearch = !this.detailedSearch;
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
