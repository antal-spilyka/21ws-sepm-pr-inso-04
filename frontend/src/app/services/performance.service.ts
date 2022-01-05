import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Globals} from '../global/globals';
import {PerformanceSearchDto} from '../dtos/performanceSearchDto';
import {Performance} from '../dtos/performance';
import {Basket} from '../dtos/basket';

@Injectable({
  providedIn: 'root'
})
export class PerformanceService {
  private messageBaseUri: string = this.globals.backendUri + '/performances';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }


  /**
   * Finds an event based on date/time, name of event and room.
   *
   * @param searchEvent dto for storing the search information.
   * @returns an array of events which suit the search query.
   */
  findPerformanceByDateTime(searchEvent: PerformanceSearchDto): Observable<Performance[]> {
    let params = new HttpParams();
    if (searchEvent.startTime && searchEvent.startTime !== '') {
      params = params.set('startTime', searchEvent.startTime);
    }
    if (searchEvent.eventName && searchEvent.eventName !== '') {
      params = params.set('eventName', searchEvent.eventName.trim());
    }
    if (searchEvent.hallName && searchEvent.hallName !== '') {
      params = params.set('hallName', searchEvent.hallName.trim());
    }
    return this.httpClient.get<Performance[]>(this.messageBaseUri + '/search', {params});
  }

  findGeneralPerformanceByDateTime(searchQuery: string): Observable<Performance[]> {
    let params = new HttpParams();
    if (searchQuery && searchQuery !== '') {
      params = params.set('searchQuery', searchQuery.trim());
    }
    return this.httpClient.get<Performance[]>(this.messageBaseUri + '/general-search', {params});
  }

  findPerformancesByArtist(id: number): Observable<Performance[]> {
    return this.httpClient.get<Performance[]>(this.messageBaseUri + '/artist/' + id);
  }

  getPerformanceById(id: number): Observable<Performance> {
    return this.httpClient.get<Performance>(this.messageBaseUri + '/' + id);
  }

  savePerformace(performance: Performance): Observable<Performance> {
    return this.httpClient.post<Performance>(this.messageBaseUri, performance);
  }

  buyPerformance(id: number, basket: Basket) {
    return this.httpClient.post<void>(this.messageBaseUri + '/buy/' + id, basket);
  }
}
