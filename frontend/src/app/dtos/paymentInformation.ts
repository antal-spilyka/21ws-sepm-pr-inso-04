export class PaymentInformation {
  constructor(
    public id: number,
    public creditCardName: string,
    public creditCardNr: string,
    public creditCardExpirationDate: string,
    public creditCardCvv: string
  ) {
  }
}
