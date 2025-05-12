import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ReservaFormComponent } from '../../../shared/components/reserva-form/reserva-form.component';

@Component({
  selector: 'rb-home',
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  constructor(private modalService: NgbModal) {}

  abrirModalReserva() {
    this.modalService.open(ReservaFormComponent, {size: 'lg'});
  }
}
