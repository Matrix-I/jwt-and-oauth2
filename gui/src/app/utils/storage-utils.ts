import { JwtAuthService } from '../core/auth/jwt-auth.service';

export class StorageUtils {
  static getToken(): string | null {
    return localStorage.getItem(JwtAuthService.JWT_TOKEN);
  }
}
