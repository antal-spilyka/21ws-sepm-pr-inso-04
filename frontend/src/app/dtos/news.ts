import {EventDto} from './eventDto';

export class News {
  id?: number;
  event: EventDto;
  rating: number;
  fsk: number;
  shortDescription?: string;
  longDescription?: string;
  createDate: Date;
}
