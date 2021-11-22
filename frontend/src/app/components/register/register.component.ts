import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormControl, Validators} from '@angular/forms';
import {countries} from '../../utils';
import {RegisterRequest} from '../../dtos/register-request';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {UserService} from "../../services/user.service";

;

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  emailControl = new FormControl('', [Validators.required, Validators.email]);
  passwordControl = new FormControl('', [Validators.required, Validators.minLength(8)]);
  firstNameControl = new FormControl('', [Validators.required]);
  lastNameControl = new FormControl('', [Validators.required]);
  phoneControl = new FormControl('', [Validators.required]);
  cityControl = new FormControl('', [Validators.required]);
  zipControl = new FormControl('', [Validators.required]);
  countryControl = new FormControl('', [Validators.required]);
  streetControl = new FormControl('', [Validators.required]);
  salutationControl = new FormControl('mr', [Validators.required]);
  disabledControl = new FormControl(false);

  firstPageOptions: FormGroup;
  secondPageOptions: FormGroup;

  next = false;
  error = false;
  hide = true;
  submitted = false;
  errorMessage = '';
  countries = countries;

  constructor(fb: FormBuilder, private userService: UserService, private router: Router) {
    this.firstPageOptions = fb.group({
      email: this.emailControl,
      password: this.passwordControl,
      firstName: this.firstNameControl,
      lastName: this.lastNameControl,
      phone: this.phoneControl,
      salutation: this.salutationControl,
      disabled: this.disabledControl,
    });

    this.secondPageOptions = fb.group({
      city: this.cityControl,
      zip: this.zipControl,
      country: this.countryControl,
      street: this.streetControl,
    });
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
  }

  ngOnInit() {
  }

  registerUser() {
    this.submitted = true;
    if (this.firstPageOptions.valid && this.secondPageOptions.valid) {
      const registerRequest = new RegisterRequest(this.emailControl.value, this.passwordControl.value,
        this.firstNameControl.value, this.lastNameControl.value, this.phoneControl.value, this.salutationControl.value,
        this.disabledControl.value, this.cityControl.value, this.zipControl.value, this.countryControl.value,
        this.streetControl.value);

      console.log('Try to authenticate user: ' + registerRequest.email);
      this.userService.createUser(registerRequest).subscribe(
        () => {
          console.log('Successfully logged in user: ' + registerRequest.email);
          this.router.navigate(['/message']);
        },
        error => {
          console.log('Could not log in due to:');
          console.log(error);
          this.error = true;
          if (typeof error.error === 'object') {
            this.errorMessage = error.error.error;
          } else {
            this.errorMessage = error.error;
          }
        }
      );
    } else {
      console.log('Invalid input');
    }
  }

  getErrorMessage() {
    if (this.emailControl.hasError('required')) {
      return 'You must enter a value';
    }
    return this.emailControl.hasError('email') ? 'Not a valid email' : '';
  }

  goNext() {
    this.next = true;
  }

  goBack() {
    this.next = false;
  }

  navigateToSignIn() {

  }
}
