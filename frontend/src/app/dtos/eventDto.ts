import {EventPlace} from './eventPlace';
import {Performance} from './performance';

export class EventDto {
  id?: number;
  name: string;
  startTime: Date;
  duration: number;
  performances: Performance[];
  eventPlace: EventPlace;
  description: string;
  category: string;
}
