import { Artist } from './artist';
import { EventDto } from './eventDto';
import {Hall} from './hall';

export class Performance {
  id?: number;
  name: string;
  startTime?: Date;
  duration: number;
  eventDto?: EventDto;
  artist: Artist;
  hall: Hall;
  priceMultiplicant: number;
}
