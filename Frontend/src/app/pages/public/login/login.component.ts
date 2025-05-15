import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../../core/services/auth/auth.service';
import { authGuard } from '../../../core/security/auth/auth.guard';
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
          this.router.navigate(['/admin/management']);
        },
        error: () => {
          window.alert('Las credenciales son incorrectas.');
        }
      });
    } else {
      this.formLogin.markAllAsTouched();
    }
  }
}
