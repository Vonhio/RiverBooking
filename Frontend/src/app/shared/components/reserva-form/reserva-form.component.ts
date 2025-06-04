import { Component, inject, Input } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { BarcoService } from '../../../core/services/barco/barco.service';
import { ReservaService } from '../../../core/services/reserva/reserva.service';
import { Barco } from '../../../interfaces/barco.model';
import { Reserva } from '../../../interfaces/reserva.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import Swal from 'sweetalert2';

@Component({
  selector: 'rb-reserva-form',
  standalone: true,
  imports: [ReactiveFormsModule,
    HttpClientModule,
    CommonModule
  ],
  templateUrl: './reserva-form.component.html',
  styleUrl: './reserva-form.component.scss'
})
export class ReservaFormComponent {

  private fb = inject(FormBuilder);
  private barcoService = inject(BarcoService);
  private reservaService = inject(ReservaService);
  private modalReserva = inject(NgbActiveModal);

  @Input() reservaEditar: Reserva | null = null;
  listaBarcos: Barco[] = [];
  horasBase: string[] = ['10:00', '12:00', '14:00', '16:00', '18:00'];
  listaHoras: string[] = [];
  plazasArray: number[] = [];
  plazasDisponibles: number = 0;
  tipoReserva: string[] = ['Compartido', 'Privado'];
  fechaMinima: string = new Date().toISOString().split('T')[0];

  mostrarSelectorHora: boolean = false;
  mostrarSelectorPlazas: boolean = false;
  hayPlazas: boolean = false;
  hayHoras: boolean = false;

  formularioReserva = this.fb.group({
    barcoId: this.fb.control<number | null>(null, Validators.required),
    fecha: this.fb.control('', Validators.required),
    hora: this.fb.control('', Validators.required),
    numPersonas: this.fb.control<number | null>(null, Validators.required),
    nombreCliente: this.fb.control('', Validators.required),
    apellidoCliente: this.fb.control('', Validators.required),
    email: this.fb.control('', [Validators.required, Validators.email]),
    telefono: this.fb.control('', [Validators.required, Validators.pattern(/^\+?[0-9]{9,12}$/)]),
    tipoReserva: this.fb.control('', Validators.required),
    precioTotal: this.fb.control<number | null>({ value: null, disabled: true }, Validators.required)
  });

  ngOnInit() {
    this.barcoService.getBarcos().subscribe((barcos) => {
      this.listaBarcos = barcos;
    });


    if (this.reservaEditar) {

      const [fecha, hora] = this.reservaEditar.fechaReserva.split('T');

      this.mostrarSelectorHora = true;
      this.mostrarSelectorPlazas = true;
      this.listaHoras = this.horasBase;

      this.formularioReserva.patchValue({
        barcoId: this.reservaEditar.barcoId,
        fecha: fecha,
        hora: hora.slice(0, 5), // Formato HH:mm
        numPersonas: this.reservaEditar.numPersonas,
        nombreCliente: this.reservaEditar.nombreCliente,
        apellidoCliente: this.reservaEditar.apellidoCliente,
        email: this.reservaEditar.email,
        telefono: this.reservaEditar.telefono,
        tipoReserva: this.reservaEditar.tipoReserva,
        precioTotal: this.reservaEditar.precioTotal
      });

      this.cambioHora();
    }
  }
  // Método para implementación de nuevas reservas. Aun no existen tales reservas, solo compartida.
  cambioTipoReserva() {

  }

  cambioBarco() {
    this.resetFechaHoraPlazas();

  }

  cambioFecha() {
    const fechaSeleccionada = this.formularioReserva.get('fecha')?.value;
    const hoy = new Date().toISOString().split('T')[0];

      if (fechaSeleccionada === hoy) {
        const horaActual = new Date().getHours();
        this.listaHoras = this.horasBase.filter(hora => {
          return horaActual < parseInt(hora.slice(0, 2))
        });
      } else {
        this.listaHoras = [...this.horasBase];
      }

      this.mostrarSelectorHora = true;
      this.mostrarSelectorPlazas = false;
      this.formularioReserva.patchValue({ hora: null, numPersonas: null });

  }

  cambioHora() {
    const { barcoId, fecha, hora, tipoReserva } = this.formularioReserva.value;

    if (barcoId && fecha && hora && tipoReserva) {
      const fechaHora = `${fecha}T${hora}`;

      this.reservaService.getInfoPlazas(fechaHora, barcoId).subscribe((respuesta) => {

        let plazasLibres: number;
        let mismaHora = hora == this.reservaEditar?.fechaReserva.split('T')[1].slice(0, 5);
        let mismoBarco = barcoId == this.reservaEditar?.barcoId;

        if (mismaHora && mismoBarco) {
          plazasLibres = respuesta.plazasDisponibles + this.reservaEditar!.numPersonas;

          this.plazasArray = Array.from({ length: plazasLibres }, (_, i) => i + 1);

        } else {
          plazasLibres = respuesta.plazasDisponibles;
          this.plazasArray = Array.from({ length: plazasLibres }, (_, i) => i + 1);
          this.formularioReserva.patchValue({ numPersonas: null });
        }

        this.plazasDisponibles = plazasLibres;
        this.mostrarSelectorPlazas = true;

      });
    }
  }

  actualizarPrecio(): void {
    const plazasReservadas = this.formularioReserva.get('numPersonas')!.value ?? 0;
    const barco = this.formularioReserva.get('barcoId')!.value;

    if (barco == 1) {
      const precioTotal = plazasReservadas * 25.00;
      return this.formularioReserva.patchValue({ precioTotal: precioTotal });
    } else {
      const precioTotal = plazasReservadas * 30.00;
      this.formularioReserva.patchValue({ precioTotal: precioTotal });
    }

  }

  enviar(): void {
    if (this.formularioReserva.valid) {
      const {
        barcoId,
        fecha,
        hora,
        numPersonas,
        nombreCliente,
        apellidoCliente,
        email,
        telefono,
        tipoReserva,
        precioTotal
      } = this.formularioReserva.getRawValue();

      const fechaHora = `${fecha}T${hora}`;
      const estado = 'Pendiente';

      const reservaDto: Reserva = {
        barcoId: barcoId!,
        fechaReserva: fechaHora!,
        numPersonas: numPersonas!,
        nombreCliente: nombreCliente!,
        apellidoCliente: apellidoCliente!,
        email: email!,
        telefono: telefono!,
        estado: estado!,
        tipoReserva: tipoReserva!,
        precioTotal: precioTotal!
      };

      if (this.reservaEditar?.id) {
        reservaDto.id = this.reservaEditar.id;
        reservaDto.codigoReserva = this.reservaEditar.codigoReserva;

        this.reservaService.modificarReserva(reservaDto).subscribe({
          next: () => {
            Swal.fire({
              icon: 'success',
              title: 'Reserva modificada',
              text: 'La reserva ha sido actualizada correctamente.',
              confirmButtonColor: '#198754'
            });
            this.barcoService.getBarcos();
            this.modalReserva.close();
          },
          error: (error) => {
            switch (error.status) {
              case 404:
                Swal.fire({
                  icon: 'error',
                  title: 'Reserva no encontrada',
                  text: 'No se pudo encontrar la reserva para modificarla.',
                  confirmButtonColor: '#dc3545'
                });
                break;
              case 409:
                Swal.fire({
                  icon: 'warning',
                  title: 'Conflicto de plazas',
                  text: 'No hay suficientes plazas disponibles para modificar la reserva.',
                  confirmButtonColor: '#ffc107'
                });
                break;
              case 500:
              default:
                Swal.fire({
                  icon: 'error',
                  title: 'Error del servidor',
                  text: 'Ha ocurrido un error inesperado al modificar la reserva.',
                  confirmButtonColor: '#dc3545'
                });
                break;
            }
          }
        });
      } else {
        this.reservaService.crearReserva(reservaDto).subscribe({
          next: () => {
            Swal.fire({
              icon: 'success',
              title: 'Reserva creada',
              text: 'La reserva se ha creado correctamente. En breves recibirá un correo con toda la información. ¡Muchas gracias por confiar en nosotros!',
              confirmButtonColor: '#198754'
            }).then(() => {
              this.modalReserva.close();
            });
            this.formularioReserva.reset();
            this.mostrarSelectorPlazas = false;
            this.mostrarSelectorHora = false;
            this.listaHoras = [];
            this.plazasArray = [];
            this.plazasDisponibles = 0;
          },
          error: (error) => {
            switch (error.status) {
              case 404:
                Swal.fire({
                  icon: 'error',
                  title: 'Barco no disponible',
                  text: 'No se ha encontrado el barco solicitado para la reserva.',
                  confirmButtonColor: '#dc3545'
                });
                break;
              case 409:
                Swal.fire({
                  icon: 'warning',
                  title: 'Plazas insuficientes',
                  text: 'No hay suficientes plazas disponibles para completar esta reserva.',
                  confirmButtonColor: '#ffc107'
                });
                break;
              case 500:
              default:
                Swal.fire({
                  icon: 'error',
                  title: 'Error del servidor',
                  text: 'Ha ocurrido un error inesperado al crear la reserva.',
                  confirmButtonColor: '#dc3545'
                });
                break;
            }
          }
        });
      }
    } else {
      this.formularioReserva.markAllAsTouched();
    }
  }

  private resetFechaHoraPlazas() {
    this.formularioReserva.patchValue({ fecha: null, hora: null, numPersonas: null, tipoReserva: '' });
    this.mostrarSelectorHora = false;
    this.mostrarSelectorPlazas = false;
    this.plazasArray = [];
  }
}
