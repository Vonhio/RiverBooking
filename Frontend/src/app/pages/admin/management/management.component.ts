import { Router } from '@angular/router';
import { Component, inject } from '@angular/core';
import { AuthService } from '../../../core/services/auth/auth.service';
import { CommonModule } from '@angular/common';
import { ReservaListComponent } from '../../../shared/components/reserva-list/reserva-list.component';
import Swal from 'sweetalert2';

@Component({
  selector: 'rb-management',
  standalone: true,
  imports: [CommonModule, ReservaListComponent],
  templateUrl: './management.component.html',
  styleUrl: './management.component.scss'
})
export class ManagementComponent {

  private auth = inject(AuthService)
  private router = inject(Router);

  logout(): void{
    this.auth.logout();
    this.router.navigate(['/admin/login']);
    Swal.fire({
      title: 'Saliendo...',
      text: 'Se ha cerrado la sesión exitosamente',
      icon: 'success',
      timer: 1500,
      timerProgressBar: true,
      showConfirmButton: false,
    });
  }
}
