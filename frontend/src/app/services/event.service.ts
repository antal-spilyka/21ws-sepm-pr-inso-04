import {HttpClient, HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EventDto } from '../dtos/eventDto';
import { EventInquiry } from '../dtos/eventInquiry';
import { Globals } from '../global/globals';
import {Address} from '../dtos/address';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private messageBaseUri: string = this.globals.backendUri + '/events';

  constructor(private httpClient: HttpClient, private globals: Globals) { }

  createEvent(eventInquiry: EventInquiry): Observable<EventDto> {
    return this.httpClient.post<EventDto>(this.messageBaseUri, eventInquiry);
  }

  findEvent(searchEvent: EventInquiry): Observable<EventDto[]> {
    let params = new HttpParams();
    if(searchEvent.duration !== null){
      params=params.set('duration', searchEvent.duration);
    }
    if(searchEvent.content !== ''){
      params=params.set('content', searchEvent.content);
    }
    if(searchEvent.categoryName !== ''){
      params=params.set('categoryName', searchEvent.categoryName);
    }
    if(searchEvent.description !== ''){
      params=params.set('description', searchEvent.description);
    }
    return this.httpClient.get<EventDto[]>(this.messageBaseUri, { params });
  }
}
