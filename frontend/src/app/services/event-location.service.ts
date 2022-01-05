import { Injectable } from '@angular/core';
import {Address} from '../dtos/address';
import {Observable} from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {EventPlace} from '../dtos/eventPlace';

@Injectable({
  providedIn: 'root'
})
export class EventLocationService {
  private eventLocationBaseUri: string = this.globals.backendUri + '/eventlocations';

  constructor(private httpClient: HttpClient, private globals: Globals) { }

  /**
   * Finds Addresses by search query.
   *
   * @param searchAddress dto for storing the search information.
   * @returns Observable List of Addresses matching query
   */
  findEventLocation(searchAddress: Address): Observable<EventPlace[]> {
    let params = new HttpParams();
    if(searchAddress.city !== '' && searchAddress.city !== null){
      params=params.set('city', searchAddress.city.trim());
    }
    if(searchAddress.state !== '' && searchAddress.state !== null){
      params=params.set('state', searchAddress.state.trim());
    }
    if(searchAddress.zip !== null && searchAddress.zip !== ''){
      params=params.set('zip', searchAddress.zip.trim());
    }
    if(searchAddress.country !== '' && searchAddress.country !== null ){
      params=params.set('country', searchAddress.country.trim());
    }
    if(searchAddress.street !== '' && searchAddress.street !== null){
      params=params.set('street', searchAddress.street.trim());
    }
    return this.httpClient.get<EventPlace[]>(this.eventLocationBaseUri, { params });
  }

  findGeneralEventLocation(searchLocation: string): Observable<EventPlace[]> {
    let params = new HttpParams();
    if(searchLocation !== '' && searchLocation !== null){
      params=params.set('searchLocation', searchLocation.trim());
    }
    return this.httpClient.get<EventPlace[]>(this.eventLocationBaseUri + '/general-search', { params });
  }

  /**
   * Find the address of the eventPlace with the given id.
   *
   * @param id of the eventPlace
   * @returns Address object connected to the eventPlace
   */
  getAddress(id: number): Observable<Address> {
    return this.httpClient.get<Address>(this.eventLocationBaseUri + '/' + id);
  }
}
