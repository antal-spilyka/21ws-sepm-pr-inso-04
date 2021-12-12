import {Component, OnInit} from '@angular/core';
import {EventDto} from '../../dtos/eventDto';
import {EventService} from '../../services/event.service';
import {Hall} from '../../dtos/hall';
import {HallService} from '../../services/hall.service';
import {EventDateTimeSearchDto} from '../../dtos/eventDateTimeSearchDto';
import {PerformanceSearchDto} from '../../dtos/performanceSearchDto';
import {PerformanceService} from '../../services/performance.service';
import {Performance} from '../../dtos/performance';

@Component({
  selector: 'app-search-time',
  templateUrl: './search-time.component.html',
  styleUrls: ['./search-time.component.scss']
})
export class SearchTimeComponent implements OnInit {
  performanceSearchDto: PerformanceSearchDto = {
    eventName: '', startTime: '', hallName: '',
  };
  performanceList: Performance[] = [];
  roomsList: Hall[] = [];
  roomMap = new Map();
  submitted = false;
  private error = false;
  private errorMessage: string;

  constructor(private performanceService: PerformanceService, private roomService: HallService) {
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
          if(this.roomsList !== []){
            for (const room of this.roomsList) {
              this.roomMap.set(room.id, room.name);
            }
          }
        }, error: error => this.handleError(error)
      }
    );
  }

  onSubmit() {
    this.performanceList = [];
    console.log(this.performanceSearchDto);
    this.performanceService.findPerformanceByDateTime(this.performanceSearchDto).subscribe(
      {
        next: performances => {
          this.submitted = true;
          console.log(this.performanceList);
          this.performanceList = performances;
          console.log(this.performanceList);
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
