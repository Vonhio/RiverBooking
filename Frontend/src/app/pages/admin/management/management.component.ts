import { Router } from '@angular/router';
import { Component, inject } from '@angular/core';
import { AuthService } from '../../../core/services/auth/auth.service';
import { CommonModule } from '@angular/common';
import { ReservaListComponent } from '../../../shared/components/reserva-list/reserva-list.component';

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
  }
}
