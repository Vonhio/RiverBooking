import { Component, inject } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ReservaFormComponent } from '../../../shared/components/reserva-form/reserva-form.component';

@Component({
  selector: 'rb-home',
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  modalService = inject(NgbModal);

  constructor() {}

  abrirModalReserva() {
    this.modalService.open(ReservaFormComponent, {size: 'md'});
  }
}
