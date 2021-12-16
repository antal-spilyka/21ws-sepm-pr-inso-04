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
    console.log('Get user with email address ', email);
    return this.httpClient.get<User>(this.registerBaseUri + '/' + email);
  }

  /**
   * Finds the users with the given email. If no email is entered, all the users will be returned.
   */
  findUsers(email: string): Observable<User[]> {
    let params = new HttpParams();
    params = params.set('email', email);
    if (email === null || email === ' ') {
      console.log('Get all users');
    } else {
      console.log('Get users with email address ', email);
    }
    return this.httpClient.get<User[]>(this.registerBaseUri + '/', {params});
  }

  /**
   * Updates the given user.
   *
   * @param user object with updated data
   */
  updateUser(user: UpdateUserRequest): Observable<string> {
    console.log('Update user with email ' + user.email);
    return this.httpClient.put(this.registerBaseUri, user, {responseType: 'text'});
  }

  /**
   * Changes the admin attribute of the given user.
   *
   * @param email of the user to be changed and the admin who sends the request.
   */
  setAdmin(email: string): Observable<string> {
    console.log('Setting admin attribute of the user with email ' + email);
    return this.httpClient.put(this.registerBaseUri + '/' + email, null, {responseType: 'text'});
  }

  /**
   * Deletes the given user.
   *
   * @param user object to delete
   */
  deleteUser(user: User) {
    console.log('Delete user with email ' + user.email);
    return this.httpClient.delete<User>(this.registerBaseUri + '/' + user.email);
  }

  /**
   * Sends a email with a generated password.
   *
   * @param user object to delete
   */
  resetPassword(email: string) {
    console.log('Reset password for user with email ' + email);
    return this.httpClient.get(this.registerBaseUri + '/' + email + '/resetPassword');
  }
}
