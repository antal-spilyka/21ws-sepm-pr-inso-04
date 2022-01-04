import { Component, OnInit } from '@angular/core';
import {TicketDetail} from '../../dtos/ticketDetail';
import {Router} from '@angular/router';
import {TicketService} from '../../services/ticket.service';
import jwt_decode from 'jwt-decode';

@Component({
  selector: 'app-tickets',
  templateUrl: './tickets.component.html',
  styleUrls: ['./tickets.component.scss']
})
export class TicketsComponent implements OnInit {

  reservedTickets: TicketDetail[];
  boughtTickets: TicketDetail[];
  error = false;
  errorMessage = '';

  constructor(private router: Router, private ticketService: TicketService) { }

  ngOnInit(): void {
    this.loadTickets();
  }

  loadTickets(): void {
    this.ticketService.getReservedTickets(this.getEmail()).subscribe(
      (tickets: TicketDetail[]) => {
        this.reservedTickets = tickets;
        console.log(this.reservedTickets);
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
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
