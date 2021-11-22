import {AuthRequest} from '../dtos/auth-request';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private registerBaseUri: string = this.globals.backendUri + '/users';

  constructor(private httpClient: HttpClient, private globals: Globals, private authService: AuthService) {
  }

  /**
   * Create the user. If it was successful, the user will be logged in
   *
   * @param authRequest User data
   */
  createUser(authRequest: AuthRequest): Observable<string> {
    return this.httpClient.post(this.registerBaseUri, authRequest, {responseType: 'text'})
      .pipe(
        tap((authResponse: string) => this.authService.loginUser({
          email: authRequest.email,
          password: authRequest.password,
        }))
      );
  }
}
