import { Artist } from './artist';
import { EventDto } from './eventDto';
import {Hall} from './hall';
import {Ticket} from './ticket';

export class PerformanceDetail {
  id?: number;
  name: string;
  startTime?: Date;
  duration: number;
  eventDto?: EventDto;
  artist: Artist;
  hall: Hall;
  tickets: Ticket[];
  priceMultiplicant: number;
}
