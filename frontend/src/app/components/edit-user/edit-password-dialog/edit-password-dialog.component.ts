import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {FormControl, Validators} from '@angular/forms';
import {AuthRequest} from '../../../dtos/auth-request';
import {AuthService} from '../../../services/auth.service';
import {DialogData} from '../edit-user.component';

@Component({
  selector: 'app-edit-password-dialog',
  templateUrl: './edit-password-dialog.component.html',
  styleUrls: ['./edit-password-dialog.component.scss']
})
export class EditPasswordDialogComponent implements OnInit {

  newPasswordControl = new FormControl('', [Validators.required, Validators.minLength(8)]);
  oldPasswordControl = new FormControl('', [Validators.required, Validators.minLength(8)]);

  hide = true;
  errorMessage = '';
  error = false;

  constructor(private authService: AuthService, public dialogRef: MatDialogRef<EditPasswordDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public dialogData: DialogData) {
  }

  ngOnInit(): void {
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
    this.errorMessage = null;
  }

  getErrorMessage(control) {
    if (control.hasError('required')) {
      return 'You must enter a value';
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
   * Form validation will start after the method is called, additionally an AuthRequest will be sent
   */
  save(): void {
    if (this.oldPasswordControl.valid && this.newPasswordControl.valid) {
      const authRequest: AuthRequest = new AuthRequest(this.dialogData.email, this.oldPasswordControl.value);
      this.authenticateUser(authRequest);
    } else {
      console.log('Invalid input');
    }
  }

  /**
   * Send authentication data to the authService. If the authentication was successfully, the password will be changed to a new pwassword
   *
   * @param authRequest authentication data from the user login form
   */
  authenticateUser(authRequest: AuthRequest): void {
    console.log('Try to authenticate user: ' + authRequest.email);
    this.authService.loginUser(authRequest).subscribe({
        next: () => {
          console.log('Right old PW');
          this.dialogRef.close(this.newPasswordControl.value);
        },
        error: (error) => {
          console.log('falsches PW');
          this.error = true;
          this.errorMessage = 'Wrong old Password';
        }
      }
    );
  }
}
