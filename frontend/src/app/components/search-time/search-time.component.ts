import {Component, OnInit} from '@angular/core';
import {EventDto} from '../../dtos/eventDto';
import {EventService} from '../../services/event.service';
import {EventInquiry} from '../../dtos/eventInquiry';
import {Room} from '../../dtos/room';
import {RoomService} from '../../services/room.service';
import {EventDateTimeSearchDto} from '../../dtos/eventDateTimeSearchDto';

@Component({
  selector: 'app-search-time',
  templateUrl: './search-time.component.html',
  styleUrls: ['./search-time.component.scss']
})
export class SearchTimeComponent implements OnInit {
  searchEvent: EventDateTimeSearchDto = {
    dateTime: null, event: '', room: '',
  };
  eventList: EventDto[] = [];
  roomsList: Room[] = [];
  roomMap = new Map();
  private error = false;
  private errorMessage: string;

  constructor(private eventService: EventService, private roomService: RoomService) {
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
    this.eventService.findEventByDateTime(this.searchEvent).subscribe(
      {
        next: events => {
          console.log(this.eventList);
          this.eventList = events;
          console.log(this.eventList);
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
