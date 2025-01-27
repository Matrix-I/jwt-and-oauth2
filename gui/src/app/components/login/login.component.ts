import { A11yModule } from '@angular/cdk/a11y';
import { Component } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { UserServiceImpl } from '../../core/service/user.service';
import { LoginRequest } from '../../../../generated-client';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { StorageUtils } from '../../utils/storage-utils';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  standalone: true,
  imports: [ReactiveFormsModule, FormsModule, A11yModule]
})
export class LoginComponent {
  signInForm: FormGroup = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(4)])
  });

  showPassword: boolean = false;

  constructor(
    private userService: UserServiceImpl,
    private router: Router
  ) {
    if (StorageUtils.getToken()) {
      this.router.navigate(['']);
    }
  }

  signIn() {
    if (this.signInForm.valid) {
      const loginRequest: LoginRequest = {
        username: this.signInForm.controls['username'].value,
        password: this.signInForm.controls['password'].value
      };
      this.userService
        .login(loginRequest)
        .pipe()
        .subscribe({
          next: () => {
            this.router.navigate(['']);
          },
          error: (error: HttpErrorResponse) => {
            alert(error.error.err);
          }
        });
    }
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  onForgotPassword() {
    console.log('Forgot password clicked');
  }
}
