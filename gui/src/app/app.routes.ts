import { Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { canActivateRedirect } from './core/guards/application-permission.guard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: '',
    pathMatch: 'full',
    canActivate: [canActivateRedirect]
  },
  {
    path: '',
    component: DashboardComponent
  },
  {
    path: 'login',
    loadChildren: () => import('./components/login/routes').then(m => m.routes)
  }
];
