import { Component, OnInit } from '@angular/core';
import {Order} from '../../dtos/order';
import {Router} from '@angular/router';
import {OrderService} from '../../services/order.service';
import jwt_decode from 'jwt-decode';
import {Performance} from '../../dtos/performance';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})
export class OrdersComponent implements OnInit {

  orders: Order[];
  reserved: Order[];
  bought: Order[];
  displayedColumns: string[] = ['performance', 'price', 'dateOfOrder', 'numberOfTickets'];

  error = false;
  errorMessage = '';

  constructor(private router: Router, private orderService: OrderService) { }

  ngOnInit(): void {
    this.loadOrders();
  }
  loadOrders(): void {
    this.orderService.getAllOrders(this.getEmail()).subscribe(
      (orders: Order[]) => {
        this.orders = orders;
        this.formatDate();
        this.reserved = orders.filter(order => order.bought === false);
        this.bought = orders.filter(order => order.bought === true);
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  formatDate(): void {
    console.log(this.orders);
    for (const val of this.orders) {
      val.dateOfOrder = new Date(val.dateOfOrder);
    }
  }

  getEmail(): string {
    const decoded: any = jwt_decode(localStorage.getItem('authToken'));
    return decoded.sub;
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  defaultServiceErrorHandling(error: any) {
    console.log(error);
    this.error = true;
    if (error.status === 0) {
      // If status is 0, the backend is probably down
      this.errorMessage = 'The backend seems not to be reachable';
    } else if (error.error.message === 'No message available') {
      // If no detailed error message is provided, fall back to the simple error name
      this.errorMessage = error.error.error;
    } else {
      this.errorMessage = error.error.message;
    }
  }

}
