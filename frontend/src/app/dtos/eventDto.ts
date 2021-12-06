import {EventPlace} from './eventPlace';
import {Performance} from './Performance';

export class EventDto {
  id?: number;
  name: string;
  startTime: Date;
  duration: number;
  performances: Performance[];
  eventPlace: EventPlace;
  description: string;
}
