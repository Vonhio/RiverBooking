import { Component, inject } from '@angular/core';
import { ColDef } from 'ag-grid-community';
import { ReservaService } from '../../../core/services/reserva/reserva.service';
import { CommonModule } from '@angular/common';
import { Reserva } from '../../../interfaces/reserva.model';
import { FormsModule } from '@angular/forms';
import { ReservaFormComponent } from '../reserva-form/reserva-form.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';
import Swal from 'sweetalert2';

@Component({
  selector: 'rb-reserva-list',
  standalone: true,
  imports: [CommonModule, FormsModule, NgxPaginationModule],
  templateUrl: './reserva-list.component.html',
  styleUrl: './reserva-list.component.scss'
})
export class ReservaListComponent {

  reservas: Reserva[] = [];
  terminoBusqueda: string = '';
  fechaFiltro: string = '';
  horaFiltro: string = '';
  horasBase: string[] = ['10:00', '12:00', '14:00', '16:00', '18:00'];
  paginaActual = 1;

  modalService = inject(NgbModal);

  private reservaService = inject(ReservaService)

  constructor() { }

  ngOnInit(): void {
    this.obtenerReservas();
  }

  obtenerReservas(): void {
    this.reservaService.getAllReservas().subscribe((reservas) => {
      this.reservas = reservas.reverse();
    });
  }

  eliminarReserva(id: number): void {
    Swal.fire({
      title: '¿Eliminar reserva?',
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#6c757d',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.reservaService.eliminarReserva(id).subscribe({
          next: () => {
            this.obtenerReservas();
            this.reservasFiltradas();
            Swal.fire({
              icon: 'success',
              title: 'Eliminada',
              text: 'La reserva se ha eliminado correctamente.',
              confirmButtonColor: '#198754'
            });
          },
          error: (err) => {
            switch (err.status) {
              case 404:
                Swal.fire({
                  icon: 'error',
                  title: 'No encontrada',
                  text: 'No se pudo encontrar la reserva a eliminar.',
                  confirmButtonColor: '#dc3545'
                });
                break;
              case 500:
              default:
                Swal.fire({
                  icon: 'error',
                  title: 'Error del servidor',
                  text: 'Ocurrió un error al intentar eliminar la reserva.',
                  confirmButtonColor: '#dc3545'
                });
                break;
            }
          }
        });
      }
    });
  }

  modificarReserva(reserva: Reserva): void {
    const modalReservas = this.modalService.open(ReservaFormComponent, { size: 'lg' });
    modalReservas.componentInstance.reservaEditar = reserva;
    modalReservas.closed.subscribe(() => {
      this.obtenerReservas();
      this.reservasFiltradas();
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
