import { Component, OnInit } from '@angular/core';
import {UserService} from '../../services/user.service';
import {User} from '../../dtos/user';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  userList: any;
  resultList: any;
  searchEmail = null;

  error = false;
  errorMessage = '';

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.getAllUsers();
    this.findUsers();
  }

  getAllUsers() {
    this.userService.findUsers(null).subscribe({
      next: (result: User[]) => {
        this.userList = result;
      },
      error: (error) => {
        this.errorMessage = error.error;
        this.error = true;
      }
    });
  }

  findUsers() {
    this.userService.findUsers(this.searchEmail).subscribe({
      next: (result: User[]) => {
        this.resultList = result;
        console.log('Initializing list of users with length: ' + this.resultList.length);
      },
      error: (error) => {
        this.errorMessage = error.error;
        this.error = true;
      }
    });
  }

  getEmail(currentUser: User) {
    if (currentUser === null || currentUser.email === null) {
      return '-';
    } else {
      return currentUser.email;
    }
  }

  getRole(currentUser: User) {
    if (currentUser === null || currentUser.admin === null) {
      return '-';
    } else {
      if (currentUser.admin) {
        return 'Administrator';
      } else {
        return 'User';
      }
    }
  }

  getPhone(currentUser: User) {
    if (currentUser === null || currentUser.phone === null) {
      return '-';
    } else {
      return currentUser.phone;
    }
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
    this.errorMessage = null;
  }

}
