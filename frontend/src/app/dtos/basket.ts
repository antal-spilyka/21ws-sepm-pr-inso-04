interface Seat {
  rowIndex: number;
  seatIndex: number;
}

export class Basket {
  constructor(
    public seats: Seat[],
    public standingPlaces: number,
    public paymentInformationId?: number
  ) {
  }
}
