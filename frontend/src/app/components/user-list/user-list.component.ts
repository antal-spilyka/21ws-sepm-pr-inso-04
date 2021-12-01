import {Component, OnInit} from '@angular/core';
import {UserService} from '../../services/user.service';
import {User} from '../../dtos/user';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  userList: any;
  searchEmail = null;
  filterToggled = false;

  error = false;
  errorMessage = '';

  constructor(private userService: UserService, private authService: AuthService) {
  }

  ngOnInit(): void {
    this.findUsers();
  }

  findUsers() {
    this.userService.findUsers(this.searchEmail).subscribe({
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

  toggleFilter() {
    if (this.filterToggled) {
      this.searchEmail = null;
      this.findUsers();
      this.filterToggled = false;
    } else {
      this.filterToggled = true;
    }
  }

  setAdmin(user: User) {
    if (user === null) {
      console.log('error user not found');
    } else {
      let newAdmin: boolean;
      // admin value of user has already been changed through the switcher
      if (user.admin) {
        newAdmin = true;
      } else {
       newAdmin = false;
      }
      const changedUser = {
        email: user.email,
        newEmail: user.email,
        admin: newAdmin,
        password: user.password,
        firstName: user.firstName,
        lastName: user.lastName,
        phone: user.phone,
        salutation: user.salutation,
        city: user.city,
        zip: user.zip,
        country: user.country,
        street: user.street,
        disabled: user.disabled,
        paymentInformation: user.paymentInformation
      };
      this.userService.updateUser(changedUser).subscribe({
        next: () => {
          console.log('User with the e-mail ' + user.email + 'changed');
        },
        error: (error) => {
          this.errorMessage = 'Admin settings of the user can not be changed: ' + error.error;
          this.error = true;
        }
      });
    }
  }

  /**
   * Returns true if the authenticated user is an admin
   */
  isAdmin(): boolean {
    const isAdmin = this.authService.getUserRole() === 'ADMIN';
    if (!isAdmin) {
      this.errorMessage = 'This functionality is only available for users with admin roles. Please contact your administrator!';
      this.error = true;
    }
    return isAdmin;
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
    this.errorMessage = null;
  }

}
