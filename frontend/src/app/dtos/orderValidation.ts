import { Ticket } from './ticket';

export class OrderValidation {
    valid: boolean;
    tickets: Ticket[];
    firstName: string;
    lastName: string;
    date: Date;
    performanceName: string;
    comment: string;
}
