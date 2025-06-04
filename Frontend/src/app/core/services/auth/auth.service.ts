import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private http = inject(HttpClient)
  private apiUrl = environment.apiUrl;
  private router = inject(Router);

  constructor() { }

  login(credencials: { username: string, password: string }): Observable<any> {
    return this.http.post<any>(this.apiUrl + '/auth/login', credencials).pipe(tap(response => {
      localStorage.setItem('token', response.token);
      localStorage.setItem('usuario', response.username)
    })
    );
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('usuario');
    this.router.navigate(['/login']);
  }
}
