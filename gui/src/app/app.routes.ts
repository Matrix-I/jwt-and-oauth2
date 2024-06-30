import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';

export const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./login/routes').then(m => m.routes)
  },
  {
    path: 'dashboard',
    pathMatch: 'full',
    component: DashboardComponent
  }
];
