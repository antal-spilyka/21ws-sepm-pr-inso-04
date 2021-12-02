import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {EventDto} from '../dtos/eventDto';
import {EventInquiry} from '../dtos/eventInquiry';
import {Globals} from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private messageBaseUri: string = this.globals.backendUri + '/events';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  createEvent(eventInquiry: EventInquiry): Observable<EventDto> {
    return this.httpClient.post<EventDto>(this.messageBaseUri, eventInquiry);
  }

  /**
   * Loads all events from the backend
   */
  findEvent(searchName: string): Observable<EventDto[]> {
    let params = new HttpParams();
    params = params.set('name', searchName);
    console.log(searchName);
    return this.httpClient.get<EventDto[]>(this.messageBaseUri, {params});
  }
}
