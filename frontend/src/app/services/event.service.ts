import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {EventDto} from '../dtos/eventDto';
import {Globals} from '../global/globals';
import {EventSearchDto} from '../dtos/eventSearchDto';
import {EventDateTimeSearchDto} from '../dtos/eventDateTimeSearchDto';
import {PerformanceSearchDto} from '../dtos/performanceSearchDto';
import {Performance} from '../dtos/performance';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private messageBaseUri: string = this.globals.backendUri + '/events';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Creates a new Event.
   *
   * @param eventDto stores information for new event.
   * @returns Observable<EventDto> with the information for the event.
   */
  saveEvent(eventDto: EventDto): Observable<EventDto> {
    console.log(`Service event ${eventDto}`);
    return this.httpClient.post<EventDto>(this.messageBaseUri, eventDto);
  }

  /**
   * Finds an event based on the duration, content, category and description.
   *
   * @param searchEvent dto for storing the search information.
   * @param pageCounter for the page to be loaded
   * @returns an array of events which suit the search query.
   */
  findEvent(searchEvent: EventSearchDto, pageCounter: number): Observable<EventDto[]> {
    let params = new HttpParams();
    if(searchEvent.duration !== null){
      params=params.set('duration', searchEvent.duration);
    }
    if(searchEvent.category !== '' && searchEvent.category !== null){
      params=params.set('category', searchEvent.category.trim());
    }
    if(searchEvent.description !== '' && searchEvent.description !== null){
      params=params.set('description', searchEvent.description.trim());
    }
    if(pageCounter !== null){
      params = params.set('page', pageCounter);
    }
    return this.httpClient.get<EventDto[]>(this.messageBaseUri, { params });
  }

  findGeneralEvent(searchString: string, pageCounter: number): Observable<EventDto[]> {
    let params = new HttpParams();
    if(searchString !== null && searchString !== ''){
      params = params.set('searchQuery', searchString);
    }
    if(pageCounter !== null){
      params = params.set('page', pageCounter);
    }
    return this.httpClient.get<EventDto[]>(this.messageBaseUri + '/general-search', { params });
  }



  /**
   * Loads all events from the backend
   */
  findEventByName(searchName: string): Observable<EventDto[]> {
    let params = new HttpParams();
    params = params.set('name', searchName);
    console.log(`searching for ${searchName}`);
    return this.httpClient.get<EventDto[]>(this.messageBaseUri + '/news', { params });
  }

  findPerformancesByEvent(id: number): Observable<Performance[]> {
    return this.httpClient.get<Performance[]>(this.messageBaseUri + '/' + id + '/performances');
  }
  findPerformancesByLocation(id: number, pageCounter: number): Observable<Performance[]> {
    let params = new HttpParams();
    params = params.set('page', pageCounter);
    return this.httpClient.get<Performance[]>(this.messageBaseUri + '/location/' + id + '/performances', { params });
  }
}
