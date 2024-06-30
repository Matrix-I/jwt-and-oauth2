import { A11yModule } from '@angular/cdk/a11y';
import { Component, OnInit } from '@angular/core';
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
import { JwtAuthService } from '../../core/auth/jwt-auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  standalone: true,
  imports: [ReactiveFormsModule, FormsModule, A11yModule]
})
export class LoginComponent implements OnInit {
  signInForm: FormGroup = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(4)])
  });

  showPassword: boolean = false;

  constructor(
    private userService: UserServiceImpl,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (localStorage.getItem(JwtAuthService.JWT_TOKEN)) {
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
