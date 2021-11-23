export class AuthRequest {
  constructor(
    public email: string,
    public password: string,
    public locked: boolean
  ) {}
}
