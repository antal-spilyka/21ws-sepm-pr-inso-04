export class User {
  constructor(
    public email: string,
    public password: string,
    public admin: boolean,
    public firstName: string,
    public lastName: string,
    public phone: string,
    public salutation: string,
    public disabled: string,
    public city: string,
    public zip: string,
    public country: string,
    public street: string,
    public lockedCounter: number
  ) {
  }
}
