import { Injectable } from '@angular/core';
import { LoginRequest, LoginResponse, UserService } from '../../../../generated-client';
import { Observable, tap } from 'rxjs';
import { JwtAuthService } from '../auth/jwt-auth.service';

@Injectable({
  providedIn: 'root'
})
export class UserServiceImpl {
  constructor(
    public userService: UserService,
    private jwtService: JwtAuthService
  ) {
    this.userService.configuration.basePath = 'http://localhost:4200/api/';
  }

  login(loginRequest: LoginRequest): Observable<LoginResponse> {
    return this.userService.login(loginRequest).pipe(
      tap((response: LoginResponse) => {
        this.jwtService.storeTokenAfterLogin(response);
      })
    );
  }

  getToken(): Observable<LoginResponse> {
    return this.userService.getToken().pipe(
      tap((response: LoginResponse) => {
        this.jwtService.storeTokenAfterLogin(response);
      })
    );
  }
}
