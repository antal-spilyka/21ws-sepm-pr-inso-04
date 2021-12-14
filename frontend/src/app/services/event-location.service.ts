import { Injectable } from '@angular/core';
import {Address} from '../dtos/address';
import {Observable} from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class EventLocationService {
  private messageBaseUri: string = this.globals.backendUri + '/eventlocations';

  constructor(private httpClient: HttpClient, private globals: Globals) { }

  /**
   * Finds Addresses by search query.
   *
   * @returns Observable List of Addresses matching query
   * @param searchAddress dto for storing the seach information.
   */
  findEventLocation(searchAddress: Address): Observable<Address[]> {
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
    return this.httpClient.get<Address[]>(this.messageBaseUri, { params });
  }
}
