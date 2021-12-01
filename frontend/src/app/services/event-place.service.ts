import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EventPlace } from '../dtos/eventPlace';
import { Globals } from '../global/globals';
import {Address} from '../dtos/address';

@Injectable({
  providedIn: 'root'
})
export class EventPlaceService {

  private messageBaseUri: string = this.globals.backendUri + '/eventplaces';

  constructor(private httpClient: HttpClient, private globals: Globals) { }

  /**
   * Finds category by name.
   *
   * @param searchName to search for
   * @returns Observable List of Category matching query
   */
  findEventPlace(searchName: string): Observable<EventPlace[]> {
    let params = new HttpParams();
    params = params.set('name', searchName);
    return this.httpClient.get<EventPlace[]>(this.messageBaseUri, { params });
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

  /**
   * Finds Addresses by search query.
   *
   * @returns Observable List of Addresses matching query
   * @param searchAddress
   */
  findEventLocation(searchAddress: Address): Observable<Address[]> {
    let params = new HttpParams();
    if(searchAddress.city !== ''){
      params=params.set('city', searchAddress.city);
    }
    if(searchAddress.state !== ''){
      params=params.set('state', searchAddress.state);
    }
    if(searchAddress.zip !== null){
      params=params.set('zip', searchAddress.zip);
    }
    if(searchAddress.country !== ''){
      params=params.set('country', searchAddress.country);
    }
    if(searchAddress.description !== ''){
      params=params.set('description', searchAddress.description);
    }
    if(searchAddress.street !== ''){
      params=params.set('street', searchAddress.street);
    }
    return this.httpClient.get<Address[]>(this.messageBaseUri, { params });
  }
}
