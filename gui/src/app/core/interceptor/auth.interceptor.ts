import { HttpInterceptorFn } from '@angular/common/http';
import { JwtAuthService } from '../auth/jwt-auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token = getJwtToken();
  if (token) {
    const cloned = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });

    return next(cloned);
  }
  return next(req);
};

function getJwtToken(): string | null {
  return localStorage.getItem(JwtAuthService.JWT_TOKEN);
}
