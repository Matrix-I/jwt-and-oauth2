import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { LoginComponent } from './login/login.component';

export const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./login/routes').then((m) => m.routes)
  }
];
