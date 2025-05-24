import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import Swal from 'sweetalert2';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const token = localStorage.getItem('token');
  if (!token) {
    Swal.fire({
      icon: 'warning',
      title: 'Área restringida',
      text: 'Debe iniciar sesión para acceder a esta área.',
      confirmButtonText: 'Iniciar sesión',
      confirmButtonColor: '#dc3545',
    }).then(() => {
      router.navigate(['/admin/login']);
    });

    return false;
  }
  return true;
};
