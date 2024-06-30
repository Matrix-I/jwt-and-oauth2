import { inject, Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { StorageUtils } from '../../utils/storage-utils';

export const canActivateRedirect: CanActivateFn = (
  _route: ActivatedRouteSnapshot,
  _state: RouterStateSnapshot
) => {
  return inject(PermissionsGuard).canActivate();
};

@Injectable({
  providedIn: 'root'
})
class PermissionsGuard {
  constructor(private _router: Router) {}

  canActivate(): Observable<boolean> {
    if (StorageUtils.getToken()) {
      this._router.navigate(['']);
    } else {
      this._router.navigate(['/login']);
    }
    return of(true);
  }
}
