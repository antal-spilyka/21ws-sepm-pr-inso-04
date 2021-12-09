import {Component, OnInit} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import jwt_decode from 'jwt-decode';
import {UserService} from '../../services/user.service';
import {countries} from '../../utils';
import {User} from '../../dtos/user';
import {Router} from '@angular/router';
import {UpdateUserRequest} from '../../dtos/updateUser-request';
import {AuthService} from '../../services/auth.service';
import {MatDialog} from '@angular/material/dialog';
import {EditEmailDialogComponent} from './edit-email-dialog/edit-email-dialog.component';
import {EditPasswordDialogComponent} from './edit-password-dialog/edit-password-dialog.component';
import {PaymentInformation} from '../../dtos/paymentInformation';
import {EditPaymentInformationDialogComponent} from './edit-payment-information-dialog/edit-payment-information-dialog.component';

export interface DialogData {
  email: string;
}

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss'],
})
export class EditUserComponent implements OnInit {
  emailControl = new FormControl('', [Validators.required, Validators.email]);
  passwordControl = new FormControl('', [Validators.required, Validators.minLength(8)]);
  firstNameControl = new FormControl('', [Validators.required]);
  lastNameControl = new FormControl('', [Validators.required]);
  phoneControl = new FormControl('', [Validators.required,
    Validators.pattern(/^[+]?[(]?[0-9]{3}[)]?[-\s.]?[0-9]{3}[-\s.]?[0-9]{4,6}$/im)]);
  cityControl = new FormControl('', [Validators.required]);
  zipControl = new FormControl('', [Validators.required]);
  countryControl = new FormControl('', [Validators.required]);
  streetControl = new FormControl('', [Validators.required]);
  salutationControl = new FormControl('mr', [Validators.required]);

  disabledControl = new FormControl();

  errorMessage = '';
  error = false;
  countries = countries;
  paymentInformations: PaymentInformation[];

  user: User;

  constructor(public dialog: MatDialog, private router: Router,
              private userService: UserService, private authService: AuthService) {
  }

  ngOnInit(): void {
    this.getUser();
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  getUser() {
    if (this.getToken() != null) {
      const decoded: any = jwt_decode(this.getToken());
      const email: string = decoded.sub;
      this.userService.get(email).subscribe(
        (user: User) => {
          this.user = user;
          this.cityControl.setValue(user.city);
          this.streetControl.setValue(user.street);
          this.zipControl.setValue(user.zip);
          this.countryControl.setValue(user.country);
          this.firstNameControl.setValue(user.firstName);
          this.lastNameControl.setValue(user.lastName);
          this.salutationControl.setValue(user.salutation);
          this.phoneControl.setValue(user.phone);
          this.emailControl.setValue(user.email);
          this.disabledControl.setValue(user.disabled);
          if (user.paymentInformation.length !== 0) {
            this.paymentInformations = user.paymentInformation;
          }
        },
        error => {
          this.defaultServiceErrorHandling(error);
        }
      );
    }
  }

  getErrorMessage(control) {
    if (control.hasError('required')) {
      return 'You must enter a value';
    }
    if (control.hasError('email')) {
      return 'Not a valid email';
    }
    if (control.hasError('minlength')) {
      return 'Not a valid length';
    }
    if (control.hasError('pattern')) {
      return 'Not a valid pattern';
    }
    return '';
  }

  deleteUser() {
    if (window.confirm('Are you sure that you want to permanently delete your account?')) {
      this.userService.deleteUser(this.user).subscribe({
        next: () => {
          this.authService.logoutUser();
          this.router.navigate(['/login']);
          window.alert('Successfully deleted the User');
        },
        error: (error) => {
          window.alert('Error during deleting User: ' + error.error.message);
        }
      });
    }
  }

  updateUser() {
    const updatedUser: UpdateUserRequest = {
      email: this.user.email, newEmail: this.emailControl.value,
      admin: this.user.admin,
      firstName: this.firstNameControl.value, lastName: this.lastNameControl.value, phone: this.phoneControl.value,
      salutation: this.salutationControl.value, street: this.streetControl.value, zip: this.zipControl.value,
      country: this.countryControl.value, city: this.cityControl.value, password: this.user.password,
      disabled: this.disabledControl.value, paymentInformation: this.paymentInformations
    };
    if (this.emailControl.dirty && !(this.emailControl.value === this.user.email)) {     //email has changed
      const dialogRef = this.dialog.open(EditEmailDialogComponent);
      dialogRef.afterClosed().subscribe(result => {
        console.log(`Dialog result: ${result}`);
        if (result) {
          this.userService.get(this.emailControl.value).subscribe({    //check if email is available
            next: () => {
              this.errorMessage = 'New Email is not available';
              this.error = true;
              this.emailControl.setValue(this.user.email);
              console.log('Email is not available');
              return false;
            },
            error: (error) => {
              if (error.status === 404) {
                console.log('Email is available');
                this.authService.logoutUser();
                this.userService.updateUser(updatedUser).subscribe({
                  next: () => {
                    window.alert('Successfully edited the User');
                    this.router.navigate(['/']);
                  },
                  // eslint-disable-next-line @typescript-eslint/no-shadow
                  error: (error) => {
                    window.alert('Error during updating User: ' + error.error.message);
                  }
                });
              }
            }
          });
        } else {
          this.emailControl.setValue(this.user.email);
        }
      });
    } else {
      this.userService.updateUser(updatedUser).subscribe(user => this.user,
        error => window.alert('Error during updating User: ' + error.error.message),
        () => {
          window.alert('Successfully edited the User');
          this.router.navigate(['/']);
        });
    }
  }

  changePassword() {
    const dialogRef = this.dialog.open(EditPasswordDialogComponent, {
      data: {email: this.user.email},
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        this.user.password = result;
      }
    });
  }

  editPaymentInformation(paymentInformation: PaymentInformation) {
    const dialogRef = this.dialog.open(EditPaymentInformationDialogComponent, {
      data: paymentInformation,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if (result != null) {
        this.paymentInformations = this.paymentInformations.filter(obj => obj !== paymentInformation);
        this.paymentInformations.push(result);
      }
    });
  }

  addPaymentInformation() {
    const dialogRef = this.dialog.open(EditPaymentInformationDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if (result != null) {
        if (!this.paymentInformations) {
          this.paymentInformations = [];
        }
        this.paymentInformations.push(result);
      }
    });
  }

  getToken() {
    return localStorage.getItem('authToken');
  }

  defaultServiceErrorHandling(error: any) {
    console.log(error);
    this.error = true;
    if (error.status === 0) {
      // If status is 0, the backend is probably down
      this.errorMessage = 'The backend seems not to be reachable';
    } else if (error.error.message === 'No message available') {
      // If no detailed error message is provided, fall back to the simple error name
      this.errorMessage = error.error.error;
    } else {
      this.errorMessage = error.error.message;
    }
  }
}


