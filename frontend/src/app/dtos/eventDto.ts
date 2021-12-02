import { Artist } from './artist';
import { Category } from './category';
import { Room } from './room';

export class EventDto {
    id?: number;
    name: string;
    duration: number;
    content: string;
    dateTime: Date;
    category: Category;
    room: Room;
    artist: Artist;
}