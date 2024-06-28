import { A11yModule } from '@angular/cdk/a11y';
import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  standalone: true,
  imports: [ReactiveFormsModule, FormsModule, A11yModule]
})
export class LoginComponent {
  signInForm: FormGroup = new FormGroup({
    emailOrPhone: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(12)])
  });

  showPassword: boolean = false;

  constructor() {
  }

  onSubmit() {
    if (this.signInForm.valid) {
      // Handle sign-in logic here
      console.log('Form submitted', this.signInForm.value);
    }
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  onForgotPassword() {
    // Handle forgot password logic
    console.log('Forgot password clicked');
  }
}
