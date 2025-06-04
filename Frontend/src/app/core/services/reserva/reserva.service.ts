import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Plazas } from '../../../interfaces/plazas.model';
import { Reserva } from '../../../interfaces/reserva.model';

@Injectable({
  providedIn: 'root'
})
export class ReservaService {

  private url = environment.apiUrl;
  private http = inject(HttpClient);

  constructor() {}

  getInfoPlazas(fechaHora: string, barcoId: number): Observable<Plazas> {
    const url = `${this.url}/reservas/plazas`;
    const params = {
      barcoId: barcoId,
      fechaHora: fechaHora
    };

    return this.http.get<Plazas>(url, { params });
  }

  crearReserva(reserva: Reserva): Observable<Reserva>{
    return this.http.post<Reserva>(`${this.url}/reservas/guardar`, reserva);
  }

  getAllReservas(): Observable<Reserva[]>{
    return this.http.get<Reserva[]>(`${this.url}/reservas`);
  }

  modificarReserva(reserva: Reserva): Observable<Reserva> {
    return this.http.put<Reserva>(`${this.url}/reservas/modificar`, reserva);
  }

  eliminarReserva(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/reservas/eliminar/${id}`);
  }
}
