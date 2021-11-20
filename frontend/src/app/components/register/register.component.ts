import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormControl, Validators} from '@angular/forms';;

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
  stateControl = new FormControl('', [Validators.required]);
  zipControl = new FormControl('', [Validators.required]);
  countryControl = new FormControl('', [Validators.required]);
  salutationControl = new FormControl('mr', [Validators.required]);
  disabledControl = new FormControl(false);

  firstPageOptions: FormGroup;
  secondPageOptions: FormGroup;

  next = false;
  hide = true;

  constructor(fb: FormBuilder) {
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
      state: this.stateControl,
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
