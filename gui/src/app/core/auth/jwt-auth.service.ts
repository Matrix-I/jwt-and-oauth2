import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { LoginResponse } from '../../../../generated-client';

@Injectable({
  providedIn: 'root'
})
export class JwtAuthService {
  public static readonly JWT_TOKEN: string = 'JWT_TOKEN';
  private isAuthenticated: Subject<boolean> = new BehaviorSubject<boolean>(false);

  constructor() {}

  storeTokenAfterLogin(response: LoginResponse): void {
    this.storeToken(response.token);
    this.isAuthenticated.next(true);
  }

  logout(): void {
    this.removeToken();
    this.isAuthenticated.next(false);
  }

  private storeToken(token: string): void {
    localStorage.setItem(JwtAuthService.JWT_TOKEN, token);
  }

  private removeToken(): void {
    localStorage.removeItem(JwtAuthService.JWT_TOKEN);
  }
}
