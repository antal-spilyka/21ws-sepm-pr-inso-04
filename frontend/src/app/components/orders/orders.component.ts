import {Component, OnInit, ViewChild} from '@angular/core';
import {Order} from '../../dtos/order';
import {Router} from '@angular/router';
import {OrderService} from '../../services/order.service';
import jwt_decode from 'jwt-decode';
import {PaymentInformationPickComponent} from '../payment-information-pick/payment-information-pick.component';
import {MatDialog} from '@angular/material/dialog';
import {PaymentInformation} from '../../dtos/paymentInformation';
import {SetOrderToBoughtDto} from '../../dtos/setOrderToBoughtDto';
import {MatTable} from '@angular/material/table';
import pdfMake from 'pdfmake/build/pdfmake';
import pdfFonts from 'pdfmake/build/vfs_fonts';

pdfMake.vfs = pdfFonts.pdfMake.vfs;
import {DomSanitizer} from '@angular/platform-browser';
import {CodeReturnDto} from 'src/app/dtos/codeReturnDto';

export interface DialogData {
  paymentInformations: PaymentInformation[];
}

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})
export class OrdersComponent implements OnInit {
  @ViewChild(MatTable) table: MatTable<any>;

  orders: Order[];
  reserved: Order[];
  bought: Order[];
  refunded: Order[];
  reservedColumns: string[] = ['performance', 'price', 'dateOfOrder', 'numberOfTickets', 'buyButton'];
  boughtColumns: string[] = ['performance', 'price', 'dateOfOrder', 'numberOfTickets', 'refundButton'];
  refundedColumns: string[] = ['performance', 'price', 'dateOfOrder', 'numberOfTickets', 'printButton'];

  error = false;
  errorMessage = '';

  constructor(
    public dialog: MatDialog,
    private router: Router,
    private orderService: OrderService,
    private sanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders(): void {
    this.orderService.getAllOrders(this.getEmail()).subscribe(
      (orders: Order[]) => {
        this.orders = orders;
        this.formatDate();
        console.log(orders);
        this.reserved = orders.filter(order => order.bought === false && order.refunded === false);
        this.bought = orders.filter(order => order.bought === true && order.refunded === false);
        this.refunded = orders.filter(order => order.refunded === true);
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
      val.price = Number(val.price.toFixed(2));
    }
  }

  getEmail(): string {
    const decoded: any = jwt_decode(localStorage.getItem('authToken'));
    return decoded.sub;
  }

  buyTicket(order: Order) {
    console.log(order.userDto.paymentInformation);
    const dialogRef = this.dialog.open(PaymentInformationPickComponent, {
      width: '50%',
      data: {paymentInformations: order.userDto.paymentInformation},
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        console.log('HALLO' + result); // todo buchung persistieren
        const setOrderToBought: SetOrderToBoughtDto = {orderId: order.id, paymentInformationId: result};
        this.orderService.setOrderToBought(setOrderToBought).subscribe({
          next: () => {
            window.alert('Successfully bought the Ticket');
            this.loadOrders();
            this.table.renderRows();
          },
          error: (error) => {
            window.alert('Error during buying process: ' + error.error.message);
          }
        });
      }
    });
  }

  refundOrder(order: Order) {
    this.orderService.refundOrder(order.id).subscribe({
      next: () => {
        window.alert('Successfully refunded the Order');
        this.loadOrders();
        this.table.renderRows();
      },
      error: (error) => {
        window.alert('Error during buying process: ' + error.error.message);
      }
    });
  }

  downloadOrder(order: Order) {
    this.orderService.downloadQRcode(order.id).subscribe({
      next: code => {
        this.downloadPdf(order, code);
      },
      error: error => {
        console.log(error);
        alert('Could not download PDF!');
        return;
      }
    });
  }

  downloadPdf(order: Order, codeReturnDto: CodeReturnDto) {
    const {performanceDto} = order;
    const {image, userName, address, tickets} = codeReturnDto;
    let addressString = address.street + ', ' + address.city + ', ' + address.zip + ', ' + address.country;
    if (addressString.length > 35) {
      addressString = addressString.substring(0, 35) + '...';
    }
    const documentDefinition = {
      content: [
        {
          fontSize: 20,
          text: `Ticket for ${performanceDto.name}`
        },
        {
          alignment: 'justify',
          columns: [
            {
              text: `
              Name:
              Date:
              Location:
              Hall:
              Price:
              Number of Tickets:`
            },
            {
              text: `
              ${userName}
              ${performanceDto.startTime ? this.renderDate(performanceDto.startTime) : 'unknown'}
              ${addressString}
              ${performanceDto.hall.name}
              ${order.price}€
              ${order.ticketDetailDtos.length}
              `
            },
            {
              image: 'qrCode',
              width: 100
            }
          ]
        },
        {
          fontSize: 15,
          text: 'Seats'
        }
      ],
      images: {
        qrCode: 'data:image/png;base64,' + image
      }
    };

    tickets.forEach(ticket => {
      const seat = ticket.ticketType === 'Seat' ? `Row ${ticket.rowIndex} Seat ${ticket.seatIndex}` : '-';
      documentDefinition.content.push({
        alignment: 'justify',
        columns: [
          {
            text: `
          Type: ${ticket.ticketType}
          Seat: ${seat}
          `
          }
        ]
      });
    });

    pdfMake.createPdf(documentDefinition).print();
  }

  downloadCancellationPdf(order: Order) {
    const {performanceDto} = order;
    let addressString = order.userDto.street + ', ' + order.userDto.city + ', ' + order.userDto.zip + ', ' + order.userDto.country;
    if (addressString.length > 35) {
      addressString = addressString.substring(0, 35) + '...';
    }
    const documentDefinition = {
      content: [
        {
          fontSize: 15,
          alignment: 'justify',
          columns: [
            {text: ''},
            {text: ''},
            {text: ''},
            {
              text: `
            ${order.userDto.firstName} ${order.userDto.lastName}
            ${order.userDto.street}
            ${order.userDto.zip} ${order.userDto.city}
            ${order.userDto.country}
            `
            }
          ]
        },
        {
          fontSize: 20,
          text: `Cancellation receipt for ${performanceDto.name}`
        },
        {
          alignment: 'justify',
          columns: [
            {
              text: `
            Date:
            Location:
            Hall:
            Price:
            Number of Tickets:`
            },
            {
              text: `
            ${performanceDto.startTime ? this.renderDate(performanceDto.startTime) : 'unknown'}
            ${addressString}
            ${performanceDto.hall.name}
            ${order.price}€
            ${order.ticketDetailDtos.length}


            `
            },
          ]
        },
        {
          fontSize: 15,
          text: 'Tickets'
        }
      ]
    };

    order.ticketDetailDtos.forEach(ticket => {
      documentDefinition.content.push({
        alignment: 'justify',
        columns: [
          {
            text: `
            Type: ${ticket.ticketType}
            `
          },
          {
            text: `
              Price: ${ticket.price}
            `
          }
        ]
      });
    });

    pdfMake.createPdf(documentDefinition).print();
  }

  renderDate(dt: Date) {
    const date = new Date(dt);
    console.log(dt);
    const d = date.getDate();
    const m = date.getMonth() + 1;
    const y = date.getFullYear();
    const h = date.getHours();
    const min = date.getMinutes();
    return d + '.' + m + '.' + y + ' ' + h + ':' + min;
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
