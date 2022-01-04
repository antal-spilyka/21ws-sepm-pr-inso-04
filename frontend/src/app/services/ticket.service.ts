import {Injectable} from '@angular/core';
import {Globals} from '../global/globals';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TicketDetail} from '../dtos/ticketDetail';

@Injectable({
  providedIn: 'root'
})
export class TicketService {
  private baseUri: string = this.globals.backendUri + '/tickets';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Gets all reserved tickets from logged in user.
   *
   * @param email of the user.
   */
  getReservedTickets(email: string): Observable<TicketDetail[]> {
    console.log('Load all reserved tickets');
    return this.httpClient.get<TicketDetail[]>(this.baseUri + '/' + email + '/reserved');
  }

  /**
   * Gets all bought tickets from logged in user.
   *
   * @param email of the user.
   */
  getBoughtTickets(email: string): Observable<TicketDetail[]> {
    console.log('Load all bought tickets');
    return this.httpClient.get<TicketDetail[]>(this.baseUri + '/' + email + '/bought');
  }
}
