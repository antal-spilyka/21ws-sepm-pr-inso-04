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

  error = false;
  errorMessage = '';

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.findUsers();
  }

  findUsers() {
    this.userService.findUsers().subscribe({
      next: (result: User[]) => {
        this.userList = result;
        console.log('Initializing list of users with length: ' + this.userList.length);
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
