import {Picture} from './picture';

export class SimpleNewsDto {
  id?: number;
  eventName: string;
  eventDate: Date;
  shortDescription?: string;
  longDescription?: string;
  createDate: Date;
  picture?: Picture;
}
