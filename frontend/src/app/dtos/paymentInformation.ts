export class PaymentInformation {
  constructor(
    public creditCardName: string,
    public creditCardNr: string,
    public creditCardExpirationDate: string,
    public creditCardCvv: string
  ) {
  }
}
