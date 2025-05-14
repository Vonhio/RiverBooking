import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../../enviroments/enviroment';
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
    return this.http.post<any>(this.apiUrl + '/login', credencials).pipe(tap(response => {
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
