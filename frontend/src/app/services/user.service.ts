import {AuthRequest} from '../dtos/auth-request';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private registerBaseUri: string = this.globals.backendUri + '/users';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Create the user. If it was successful, the user will be logged in
   *
   * @param authRequest User data
   */
  createUser(authRequest: AuthRequest): Observable<string> {
    return this.httpClient.post(this.registerBaseUri, authRequest, {responseType: 'text'});
  }
}
