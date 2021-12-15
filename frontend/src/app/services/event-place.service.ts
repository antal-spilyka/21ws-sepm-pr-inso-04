import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {EventPlace} from '../dtos/eventPlace';
import {Globals} from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class EventPlaceService {

  private messageBaseUri: string = this.globals.backendUri + '/eventplaces';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Finds category by name.
   *
   * @param searchName to search for
   * @returns Observable List of Category matching query
   */
  findEventPlace(searchName: string): Observable<EventPlace[]> {
    let params = new HttpParams();
    params = params.set('name', searchName);
    return this.httpClient.get<EventPlace[]>(this.messageBaseUri, {params});
  }

  /**
   * Persists new eventPlace.
   *
   * @param eventPlace to be persisted
   * @returns Observable
   */
  createEventPlace(eventPlace: EventPlace): Observable<EventPlace> {
    console.log(eventPlace);
    return this.httpClient.post<EventPlace>(this.messageBaseUri, eventPlace);
  }

}
