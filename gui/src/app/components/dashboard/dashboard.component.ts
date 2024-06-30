import { Component, OnInit } from '@angular/core';
import { UserServiceImpl } from '../../core/service/user.service';
import { JwtAuthService } from '../../core/auth/jwt-auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  constructor(private userService: UserServiceImpl) {}

  ngOnInit(): void {
    if (!localStorage.getItem(JwtAuthService.JWT_TOKEN)) {
      this.userService.getToken().subscribe();
    }
  }
}
