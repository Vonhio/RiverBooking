import { Routes } from '@angular/router';
import { HomeComponent } from './pages/public/home/home.component';
import { LoginComponent } from './pages/public/login/login.component';
import { ManagementComponent } from './pages/admin/management/management.component';
import { authGuard } from './core/security/auth/auth.guard';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path:'admin/login',
    component: LoginComponent
  },
  {
    path:'admin/management',
    canActivate: [authGuard],
    component: ManagementComponent
  },

  { path: '**', redirectTo: '' }
];
