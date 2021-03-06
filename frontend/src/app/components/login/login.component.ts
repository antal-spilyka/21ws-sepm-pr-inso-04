import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from '../../services/auth.service';
import {AuthRequest} from '../../dtos/auth-request';
import {User} from '../../dtos/user';
import {UserService} from '../../services/user.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  emailControl = new FormControl('', [Validators.required, Validators.email]);
  passwordControl = new FormControl('', [Validators.required, Validators.minLength(8)]);

  loginForm: FormGroup;
  // After first submission attempt, form validation will start
  submitted = false;
  // Error flag
  error = false;
  errorMessage = '';

  // Indicates if the user wants to hide the password
  hide = true;

  // Counts the number of wrong tries for the password
  passwordCounter = 0;

  // Display error message if the login was not successful
  wrongLogin = false;

  constructor(private formBuilder: FormBuilder,
              private authService: AuthService,
              private userService: UserService,
              private router: Router) {
    this.loginForm = this.formBuilder.group({
      email: this.emailControl,
      password: this.passwordControl
    });
  }

  /**
   * Form validation will start after the method is called, additionally an AuthRequest will be sent
   */
  loginUser() {
    this.submitted = true;
    if (this.loginForm.valid) {
      const authRequest: AuthRequest = new AuthRequest(this.loginForm.controls.email.value, this.loginForm.controls.password.value);
      this.authenticateUser(authRequest);
    } else {
      console.log('Invalid input');
    }
  }

  /**
   * Send authentication data to the authService. If the authentication was successfully, the user will be forwarded to the message page
   *
   * @param authRequest authentication data from the user login form
   */
  authenticateUser(authRequest: AuthRequest) {
    console.log('Try to authenticate user: ' + authRequest.email);
    this.authService.loginUser(authRequest).subscribe({
        next: () => {
          // Resetting the number of wrong passwords
          this.passwordCounter = 0;
          this.wrongLogin = false;

          console.log('Successfully logged in user: ' + authRequest.email);
          this.router.navigate(['/']);
        },
        error: (error) => {
          // Hide error message in the form
          this.wrongLogin = true;

          // Load the number of wrong password tries for the user
          this.userService.get(this.emailControl.value).subscribe({
            next: (user: User) => {
              this.passwordCounter = user.lockedCounter;
            },
            error: (err) => {
              console.log(err);
            }
          });

          console.log('Could not log in due to:');
          console.log(error);
          this.error = true;
          this.errorMessage = 'Bad credentials';
        }
      }
    );
  }

  resetEmail() {
    if (this.emailControl.valid) {
      this.userService.resetPassword(this.emailControl.value).subscribe({
        next: () => {
          window.alert('Please check your email inbox and your spam');
        },
        error: (err) => {
          this.error = true;
          this.errorMessage = 'There is something wrong';
        }
      });
    } else {
      this.error = true;
      this.errorMessage = 'Not a valid email';
    }
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  getErrorMessage() {
    if (this.emailControl.hasError('required')) {
      return 'You must enter a value';
    }
    return this.emailControl.hasError('email') ? 'Not a valid email' : '';
  }

  ngOnInit() {
  }

}
