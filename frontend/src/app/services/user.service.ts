import {AuthRequest} from '../dtos/auth-request';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {User} from '../dtos/user';
import {UpdateUserRequest} from '../dtos/updateUser-request';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private registerBaseUri: string = this.globals.backendUri + '/users';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Create the user. If it was successful, the user will be logged in.
   *
   * @param authRequest User data
   */
  createUser(authRequest: AuthRequest): Observable<string> {
    console.log(authRequest);
    return this.httpClient.post(this.registerBaseUri, authRequest, {responseType: 'text'});
  }

  /**
   * Returns the user with the given e-mail address.
   *
   * @param email of the user
   */
  get(email: string): Observable<User> {
    let params = new HttpParams();
    params = params.set('email', email);
    console.log('Get user with email address ', email);
    return this.httpClient.get<User>(this.registerBaseUri + '/', {params});
  }

  /**
   * Finds the users with the given email. If no email is entered, all the users will be returned.
   */
  findUsers(email: string): Observable<User[]> {
    let params = new HttpParams();
    params = params.set('email', email);
    console.log('Get users with email address ', params);
    return this.httpClient.get<User[]>(this.registerBaseUri + '/', {params});
  }

  /**
   * Updates User
   *
   * @param user object with updated data
   */
  updateUser(user: UpdateUserRequest): Observable<string> {
    console.log("new admin: " + user.admin);
    console.log('Update user with email ' + user.email);
    return this.httpClient.put(this.registerBaseUri, user, {responseType: 'text'});
  }
}
