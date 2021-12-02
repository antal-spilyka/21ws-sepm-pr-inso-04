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
