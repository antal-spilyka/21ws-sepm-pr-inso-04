export class RegisterRequest {
  constructor(
    public email: string,
    public password: string,
    public firstName: string,
    public lastName: string,
    public phone: string,
    public salutation: string,
    public disabled: string,
    public city: string,
    public zip: string,
    public country: string,
    public street: string,
  ) {
  }
}
