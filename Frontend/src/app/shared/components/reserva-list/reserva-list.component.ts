import { Component, inject } from '@angular/core';
import { ColDef } from 'ag-grid-community';
import { ReservaService } from '../../../core/services/reserva/reserva.service';
import { CommonModule } from '@angular/common';
import { Reserva } from '../../../interfaces/reserva.model';
import { FormsModule } from '@angular/forms';
import { ReservaFormComponent } from '../reserva-form/reserva-form.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'rb-reserva-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reserva-list.component.html',
  styleUrl: './reserva-list.component.scss'
})
export class ReservaListComponent {

  reservas: Reserva[] = [];
  terminoBusqueda: string = '';
  fechaFiltro: string = '';
  horaFiltro: string = '';
  horasBase: string[] = ['10:00', '12:00', '14:00', '16:00', '18:00'];

  modalService = inject(NgbModal);

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
        this.reservasFiltradas();
        alert('Reserva eliminada correctamente')
      },
      error: (err) => {
        alert('No se pudo eliminar la reserva')
      }
    });
  }

  modificarReserva(reserva: Reserva): void {
    const modalReservas = this.modalService.open(ReservaFormComponent, { size: 'lg' });
    modalReservas.componentInstance.reservaEditar = reserva;
    modalReservas.closed.subscribe({
      next: () => {
        this.obtenerReservas();
        this.reservasFiltradas();
      },
      error: () => {
        alert('No se pudo modificar la reserva')
      }
    });
  }

  reservasFiltradas(): Reserva[] {
  const termino = this.terminoBusqueda.toLowerCase();

  return this.reservas.filter(r => {
    const coincideTexto =
      r.codigoReserva?.toLowerCase().includes(termino) ||
      `${r.nombreCliente} ${r.apellidoCliente}`.toLowerCase().includes(termino);

    const coincideFecha =
      !this.fechaFiltro || r.fechaReserva.startsWith(this.fechaFiltro);

    const coincideHora =
      !this.horaFiltro || r.fechaReserva.includes(this.horaFiltro);

    return coincideTexto && coincideFecha && coincideHora;
  });
}
}
