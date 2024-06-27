import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  emailControl: FormControl = new FormControl();

  onSubmit() {
    console.log('Email submitted:', this.emailControl.value);
  }
}
