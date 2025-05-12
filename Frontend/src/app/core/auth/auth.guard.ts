import { CanActivateFn } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const token = localStorage.getItem('token');
  if (!token) {
    window.alert('Acceso denegado. Debes iniciar sesión.');
    window.location.href = '/admin/login';
    return false;
  }
  return true;
};
