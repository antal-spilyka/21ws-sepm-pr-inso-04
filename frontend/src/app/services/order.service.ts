import {Injectable} from '@angular/core';
import {Globals} from '../global/globals';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Order} from '../dtos/order';
import {SetOrderToBoughtDto} from '../dtos/setOrderToBoughtDto';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private baseUri: string = this.globals.backendUri + '/orders';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Gets all reserved orders from logged in user.
   *
   * @param email of the user.
   */
  getReservedOrders(email: string): Observable<Order[]> {
    console.log('Load all reserved orders');
    return this.httpClient.get<Order[]>(this.baseUri + '/' + email + '/reserved');
  }

  /**
   * Gets all bought orders from logged in user.
   *
   * @param email of the user.
   */
  getBoughtOrders(email: string): Observable<Order[]> {
    console.log('Load all bought orders');
    return this.httpClient.get<Order[]>(this.baseUri + '/' + email + '/bought');
  }

  /**
   * Gets all orders from logged in user.
   *
   * @param email of the user.
   */
  getAllOrders(email: string): Observable<Order[]> {
    console.log('Load all orders');
    return this.httpClient.get<Order[]>(this.baseUri + '/' + email);
  }

  /**
   * Method for setting an order to bought and update the paymentInformation for the order.
   *
   * @param setOrderToBoughtDto
   */
  setOrderToBought(setOrderToBoughtDto: SetOrderToBoughtDto): Observable<string> {
    console.log('Set order to bought');
    return this.httpClient.put(this.baseUri, setOrderToBoughtDto, {responseType: 'text'});
  }

  refundOrder(orderId: number): Observable<void> {
    return this.httpClient.put<void>(this.baseUri + '/refund', {orderId});
  }
}
