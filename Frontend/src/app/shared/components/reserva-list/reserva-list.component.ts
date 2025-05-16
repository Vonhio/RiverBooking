import { Component, inject } from '@angular/core';
import { ColDef } from 'ag-grid-community';
import { ReservaService } from '../../../core/services/reserva/reserva.service';
import { CommonModule } from '@angular/common';
import { Reserva } from '../../../interfaces/reserva.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'rb-reserva-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reserva-list.component.html',
  styleUrl: './reserva-list.component.scss'
})
export class ReservaListComponent {

  reservas: Reserva[] = [];

  private reservaService = inject(ReservaService)

  constructor() { }

  ngOnInit(): void {
    this.obtenerReservas();
  }

  obtenerReservas(): void {
    this.reservaService.getAllReservas().subscribe((reservas) => {
      this.reservas = reservas;
    });
  }

  eliminarReserva(id: number): void {
    if (!confirm('¿Estás seguro de eliminar esta reserva')) {
      return;
    }
    this.reservaService.eliminarReserva(id).subscribe({
      next: () => {
        this.obtenerReservas();
        alert('Reserva eliminada correctamente')
      },
      error: (err) => {
        alert('No se pudo eliminar la reserva')
      }
    });
  }
}
