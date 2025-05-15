import { Component, Input } from '@angular/core';
import { ICellRendererParams } from 'ag-grid-community';

@Component({
  selector: 'rb-acciones',
  standalone: true,
  imports: [],
  templateUrl: './acciones.component.html',
  styleUrl: './acciones.component.scss'
})
export class AccionesComponent {
  @Input() params!: ICellRendererParams;

  editarReserva(){

  }

  eliminarReserva(){

  }
}
