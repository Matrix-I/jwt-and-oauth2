import { Injectable } from '@angular/core';
import { LoginRequest, LoginResponse, UserService } from '../../../../generated-client';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserServiceImpl {
  constructor(public userService: UserService) {}

  login(loginRequest: LoginRequest): Observable<LoginResponse> {
    return this.userService.login(loginRequest);
  }

  test() {
    const token: string = localStorage.getItem('access_token') || '';
    const headers = new HttpHeaders().set('Authorization', token);
    const options = { headers };
    // return this.userService.test(undefined, undefined, options);
  }
}
