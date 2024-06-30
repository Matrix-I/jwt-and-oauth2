import { Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';

export const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./components/login/routes').then(m => m.routes)
  },
  {
    path: 'dashboard',
    pathMatch: 'full',
    component: DashboardComponent
  }
];
