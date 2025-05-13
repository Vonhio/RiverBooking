import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../enviroments/enviroment';
import { Barco } from '../../../interfaces/barco.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BarcoService {

  private url = environment.apiUrl;
  private http = inject(HttpClient);

  constructor() {}

  getBarcos(): Observable<Barco[]> {
    return this.http.get<Barco[]>(`${this.url}/barcos`);
  }
}
