import { Component, OnInit } from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import jwt_decode from "jwt-decode";
import {UserService} from "../../services/user.service";
import {countries} from '../../utils';
import {User} from "../../dtos/user";


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

  creditCardNumberControl = new FormControl('', [Validators.required,
    Validators.pattern(/^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\d{3})\d{11})$/im)]);

  creditCardExperationMonthControl = new FormControl('', [Validators.required,
    Validators.pattern(/^0[1-9]|1[0-2]$/im)]);
  creditCardExperationYearControl = new FormControl('', [Validators.required, Validators.pattern(/^19[5-9]\d|20[0-4]\d|2050$/im)]);
  creditCardCsvControl = new FormControl('', [Validators.required, Validators.pattern(/^[0-9]{3}$/im)]);
  disabledControl = new FormControl(false);

  errorMessage = '';
  error = false;
  countries = countries;

  user: User;

  constructor(private userService: UserService) { }

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
      console.log('email: ' + email);
      //const email = 'ma%40asdg.avfd';
      this.userService.get(email).subscribe(
        (user: User) => {
          this.user = user;
          console.log(this.user);
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
