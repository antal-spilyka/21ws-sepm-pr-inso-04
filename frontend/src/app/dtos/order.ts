import {User} from './user';
import {Performance} from './performance';
import {TicketDetail} from './ticketDetail';

export class Order {
  id: number;
  performanceDto: Performance;
  userDto?: User;
  ticketDetailDtos: TicketDetail[];
  price: number;
  bought: boolean;
  dateOfOrder: Date;
  refunded: boolean;
}
