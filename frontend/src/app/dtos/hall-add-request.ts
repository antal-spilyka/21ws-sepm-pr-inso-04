import {IHallplanElement, Sector} from '../components/create-hallplan/types';

export class HallAddRequest {
  constructor(
    public name: string,
    public rows: IHallplanElement[][],
    public sectors: Sector[],
    public standingPlaces: number,
  ) {
  }
}
