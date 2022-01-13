import { Address } from './address';
import { Ticket } from './ticket';

export class CodeReturnDto {
    image: string;
    userName: string;
    address: Address;
    tickets: Ticket[];
}
