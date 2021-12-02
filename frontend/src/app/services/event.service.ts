import {HttpClient, HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EventDto } from '../dtos/eventDto';
import { EventInquiry } from '../dtos/eventInquiry';
import { Globals } from '../global/globals';
import {EventSearchDto} from '../dtos/eventSearchDto';
import {EventDateTimeSearchDto} from '../dtos/eventDateTimeSearchDto';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private messageBaseUri: string = this.globals.backendUri + '/events';

  constructor(private httpClient: HttpClient, private globals: Globals) { }

  /**
   * Creates a new Event.
   *
   * @param eventInquiry stores information for new event.
   * @returns Observable<EventDto> with the information for the event.
   */
  createEvent(eventInquiry: EventInquiry): Observable<EventDto> {
    return this.httpClient.post<EventDto>(this.messageBaseUri, eventInquiry);
  }

  /**
   * Finds an event based on the duration, content, category and description.
   *
   * @param searchEvent dto for storing the search information.
   * @returns an array of events which suit the search query.
   */
  findEvent(searchEvent: EventSearchDto): Observable<EventDto[]> {
    let params = new HttpParams();
    if(searchEvent.duration !== null){
      params=params.set('duration', searchEvent.duration);
    }
    if(searchEvent.content !== '' && searchEvent.content !== null){
      params=params.set('content', searchEvent.content.trim());
    }
    if(searchEvent.categoryName !== '' && searchEvent.categoryName !== null){
      params=params.set('categoryName', searchEvent.categoryName.trim());
    }
    if(searchEvent.description !== '' && searchEvent.description !== null){
      params=params.set('description', searchEvent.description.trim());
    }
    return this.httpClient.get<EventDto[]>(this.messageBaseUri, { params });
  }

  /**
   * Finds an event based on date/time, name of event and room.
   *
   * @param searchEvent dto for storing the search information.
   * @returns an array of events which suit the search query.
   */
  findEventByDateTime(searchEvent: EventDateTimeSearchDto): Observable<EventDto[]>{
    let params = new HttpParams();
    if(searchEvent.dateTime !== null){
      params=params.set('dateTime', searchEvent.dateTime.toDateString());
    }
    if( searchEvent.event && searchEvent.event !== '' ){
      params=params.set('event', searchEvent.event.trim());
    }
    if(searchEvent.room && searchEvent.room !== ''){
      params=params.set('room', searchEvent.room.trim());
    }
    return this.httpClient.get<EventDto[]>(this.messageBaseUri + '/dateTime', { params });
  }
}
