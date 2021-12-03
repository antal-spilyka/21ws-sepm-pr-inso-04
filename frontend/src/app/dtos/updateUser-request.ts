import {PaymentInformation} from './paymentInformation';

export class UpdateUserRequest {
  constructor(
    public email: string,
    public newEmail: string,
    public admin: boolean,
    public password: string,
    public firstName: string,
    public lastName: string,
    public phone: string,
    public salutation: string,
    public city: string,
    public zip: string,
    public country: string,
    public street: string,
    public disabled: boolean,
    public paymentInformation: PaymentInformation
  ) {
  }
}
