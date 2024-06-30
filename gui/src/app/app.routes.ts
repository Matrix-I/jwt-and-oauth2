import { Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';

export const routes: Routes = [
  {
    path: 'login',
    loadChildren: () => import('./components/login/routes').then(m => m.routes)
  },
  {
    path: '',
    component: DashboardComponent
  }
];
