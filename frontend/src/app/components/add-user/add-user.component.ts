import {Component, OnInit, ViewChild} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {countries} from '../../utils';
import {EditPaymentInformationDialogComponent}
  from '../edit-user/edit-payment-information-dialog/edit-payment-information-dialog.component';
import {MatDialog} from '@angular/material/dialog';
import {PaymentInformation} from '../../dtos/paymentInformation';
import {MatTable} from '@angular/material/table';
import {UpdateUserRequest} from '../../dtos/updateUser-request';
import {UserService} from '../../services/user.service';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent implements OnInit {
  @ViewChild(MatTable) table: MatTable<any>;

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
  adminControl = new FormControl();

  hide = true;

  paymentInformations: PaymentInformation[];

  countries = countries;
  displayedColumns: string[] = ['name', 'number', 'expirationDate', 'cvv', 'editButton', 'deleteButton'];

  errorMessage = '';
  error = false;

  constructor(private router: Router, public dialog: MatDialog, private userService: UserService, private authService: AuthService) {
    this.disabledControl.setValue(false);
    this.adminControl.setValue(false);
  }

  ngOnInit(): void {
  }

  saveUser() {
    const updatedUser: UpdateUserRequest = {
      email: this.emailControl.value, newEmail: null,
      admin: this.adminControl.value,
      firstName: this.firstNameControl.value, lastName: this.lastNameControl.value, phone: this.phoneControl.value,
      salutation: this.salutationControl.value, street: this.streetControl.value, zip: this.zipControl.value,
      country: this.countryControl.value, city: this.cityControl.value, password: this.passwordControl.value,
      disabled: this.disabledControl.value, paymentInformation: this.paymentInformations
    };
    console.log(updatedUser);
    this.userService.get(this.emailControl.value).subscribe({    //check if email is available
      next: () => {
        this.errorMessage = 'New Email is not available';
        this.error = true;
        this.emailControl.setValue('');
        console.log('Email is not available');
        return false;
      },
      error: (error) => {
        if (error.status === 404) {
          console.log('Email is available');
          this.userService.addUser(updatedUser).subscribe({
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
  }

  isAdmin(): boolean {
    const isAdmin = this.authService.getUserRole() === 'ADMIN';
    if (!isAdmin) {
      this.errorMessage = 'This functionality is only available for users with admin roles. Please contact your administrator!';
      this.error = true;
    }
    return isAdmin;
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
        if (this.table) {
          this.table.renderRows();
        }
      }
    });
  }

  deletePaymentInformation(paymentInformation: PaymentInformation) {
    this.paymentInformations = this.paymentInformations.filter(obj => obj !== paymentInformation);
    this.table.renderRows();
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
        this.table.renderRows();
      }
    });
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

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
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
