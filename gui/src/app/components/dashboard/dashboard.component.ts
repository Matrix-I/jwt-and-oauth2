import { Component, OnInit } from '@angular/core';
import { UserServiceImpl } from '../../core/service/user.service';
import { JwtAuthService } from '../../core/auth/jwt-auth.service';
import { StorageUtils } from '../../utils/storage-utils';
import { catchError, of } from 'rxjs';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  constructor(
    private userService: UserServiceImpl,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (!StorageUtils.getToken()) {
      this.userService
        .getToken()
        .pipe(
          catchError(err => {
            this.router.navigate(['/login']);
            return of(null);
          })
        )
        .subscribe();
    }
  }
}
