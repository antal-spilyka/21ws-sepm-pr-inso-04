import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Room } from '../dtos/room';
import { RoomInquiry } from '../dtos/RoomInquiry';
import { RoomSearch } from '../dtos/roomSearch';
import { Globals } from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  private messageBaseUri: string = this.globals.backendUri + '/rooms';

  constructor(private httpClient: HttpClient, private globals: Globals) { }

  /**
   * Finds room by name and (exact) eventPlaceName.
   *
   * @param roomSearch to search for
   * @returns Observable List of Rooms matching query
   */
  findRoom(roomSearch: RoomSearch): Observable<Room[]> {
    let params = new HttpParams();
    params = params.set('name', roomSearch.name);
    params = params.set('eventPlaceName', roomSearch.eventPlaceName);
    return this.httpClient.get<Room[]>(this.messageBaseUri, { params });
  }

  /**
   * Persists new room.
   *
   * @param roomInquiry to be persisted
   * @returns Observable
   */
  createRoom(roomInquiry: RoomInquiry): Observable<Room> {
    console.log(roomInquiry);
    return this.httpClient.post<Room>(this.messageBaseUri, roomInquiry);
  }
  /**
   * Get list with all rooms.
   *
   * @returns Observable of Room.
   */
  getAllRooms(): Observable<Room[]> {
    console.log('Getting the list of all rooms');
    return this.httpClient.get<Room[]>(this.messageBaseUri + '/search');
  }
}
