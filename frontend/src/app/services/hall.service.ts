import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Hall} from '../dtos/hall';
import {Globals} from '../global/globals';
import {HallAddRequest} from '../dtos/hall-add-request';

@Injectable({
  providedIn: 'root'
})
export class HallService {

  private messageBaseUri: string = this.globals.backendUri + '/halls';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Finds room by name and (exact) eventPlaceName.
   *
   * @param hallName to search for
   * @returns Observable List of Rooms matching query
   */
  findHall(hallName: string): Observable<Hall[]> {
    let params = new HttpParams();
    params = params.set('name', hallName);
    return this.httpClient.get<Hall[]>(this.messageBaseUri, {params});
  }

  findHallWithPlace(hallName: string, eventPlaceId: number): Observable<Hall[]> {
    let params = new HttpParams();
    params = params.set('name', hallName);
    params = params.set('eventPlaceId', eventPlaceId);
    console.log(eventPlaceId);
    return this.httpClient.get<Hall[]>(this.messageBaseUri + '/eventplaces', {params});
  }

  /**
   * Persists new room.
   *
   * @param hall to be persisted
   * @returns Observable
   */
  saveHall(hall: Hall): Observable<Hall> {
    console.log(`Hall service: ${hall.eventPlaceDto}`);
    return this.httpClient.post<Hall>(this.messageBaseUri, hall);
  }
  /**
   * Get list with all rooms.
   *
   * @returns Observable of Hall.
   */
  getAllRooms(): Observable<Hall[]> {
    console.log('Getting the list of all rooms');
    return this.httpClient.get<Hall[]>(this.messageBaseUri + '/search');
  }

  getHallId(hallId: number): Observable<HallAddRequest> {
    return this.httpClient.get<HallAddRequest>(this.messageBaseUri + '/' + hallId);
  }
}
