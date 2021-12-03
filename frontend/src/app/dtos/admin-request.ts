export class AdminRequest {
  constructor(
    public adminEmail: string,
    public email: string,
    public admin: boolean,
  ) {
  }
}
