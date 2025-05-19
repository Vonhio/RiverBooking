import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../../core/services/auth/auth.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'rb-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  private fb = inject(FormBuilder);
  private auth = inject(AuthService);
  private router = inject(Router);
  constructor() { }
  formLogin = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });
  login() {
    if (this.formLogin.valid) {
      const username = this.formLogin.value.username;
      const password = this.formLogin.value.password;

      const loginData: any = {
        username: username,
        password: password
      }

      this.auth.login(loginData).subscribe({
        next: () => {
          Swal.fire({
            icon: 'success',
            title: 'Bienvenido',
            text: 'Inicio de sesión correcto.',
            confirmButtonText: 'Continuar',
            confirmButtonColor: '#198754'
          }).then(() => {
            this.router.navigate(['/admin/management']);
          });
        },
        error: (err) => {
          if (err.status === 401) {
            Swal.fire({
              icon: 'warning',
              title: 'Acceso denegado',
              text: 'Usuario o contraseña incorrectos.',
              confirmButtonColor: '#ffc107'
            });
          } else {
            Swal.fire({
              icon: 'error',
              title: 'Ha ocurrido un error',
              text: 'Algo salió mal. Inténtalo más tarde.',
              confirmButtonColor: '#dc3545'
            });
          }
        }
      });
    } else {
      this.formLogin.markAllAsTouched();
    }
  }
}
