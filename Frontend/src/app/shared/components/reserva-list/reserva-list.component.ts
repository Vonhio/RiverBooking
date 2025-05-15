import { Component, inject } from '@angular/core';
import { AgGridAngular } from 'ag-grid-angular';
import { ColDef } from 'ag-grid-community';
import { ReservaService } from '../../../core/services/reserva/reserva.service';
import { CommonModule } from '@angular/common';
import { Reserva } from '../../../interfaces/reserva.model';

@Component({
  selector: 'rb-reserva-list',
  standalone: true,
  imports: [CommonModule, AgGridAngular],
  templateUrl: './reserva-list.component.html',
  styleUrl: './reserva-list.component.scss'
})
export class ReservaListComponent {
  rowData: Reserva[] = [];
  colDefs: ColDef[] = [
    { field: 'id', headerName: 'ID', hide: true },
    { field: 'fechaReserva',
    headerName: 'Fecha de reserva',
    filter: 'agDateColumnFilter',
    sort: 'asc',
    width: 180
  },
  { field: 'tipoReserva', headerName: 'Tipo', filter: true, width: 140 },
  {
    field: 'numPersonas',
    headerName: 'Personas',
    filter: 'agNumberColumnFilter',
    width: 120
  },
  { field: 'nombreCliente', headerName: 'Nombre', filter: true, width: 160 },
  { field: 'apellidoCliente', headerName: 'Apellidos', filter: true, width: 190 },
  { field: 'telefono', headerName: 'Teléfono', width: 140 },
  { field: 'email', headerName: 'E-mail', width: 220 },
  {
    field: 'estado',
    headerName: 'Estado',
    width: 130
  },
  {
    field: 'barcoId',
    headerName: 'Barco',
    filter: 'agNumberColumnFilter',
    width: 110
  },
  {
    field: 'precioTotal',
    headerName: 'Precio (€)',
    filter: 'agNumberColumnFilter',
    width: 130,
    valueFormatter: ({ value }) =>
      value != null
        ? Number(value).toLocaleString('es-ES', {
            style: 'currency',
            currency: 'EUR'
          })
        : ''
  },
  { field: 'codigoReserva', headerName: 'Código', filter: true, width: 160 }
  ];

  private reservaService = inject(ReservaService)

  constructor() { }

  ngOnInit(): void{
    this.obtenerReservas();
  }

  obtenerReservas(): void {
    this.reservaService.getAllReservas().subscribe((reservas) => {
      this.rowData = reservas;
    });
  }
}
