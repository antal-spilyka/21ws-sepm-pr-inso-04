import { EventDto } from './eventDto';
import { Picture } from './picture';

export class News {
  id?: number;
  event: EventDto;
  rating: number;
  fsk: number;
  shortDescription?: string;
  longDescription?: string;
  createDate: Date;
  pictures?: Picture[];
}
