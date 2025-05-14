import { Component, inject } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { BarcoService } from '../../../core/services/barco/barco.service';
import { ReservaService } from '../../../core/services/reserva/reserva.service';
import { Barco } from '../../../interfaces/barco.model';
import { Reserva } from '../../../interfaces/reserva.model';

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
  private http = inject(HttpClient);
  private barcoService = inject(BarcoService);
  private reservaService = inject(ReservaService);


  listaBarcos: Barco[] = [];
  horasBase: string[] = ['10:00', '12:00', '14:00', '16:00', '18:00'];
  listaHoras: string[] = [];
  plazasArray: number[] = [];
  plazasDisponibles: number = 0;
  tipoReserva: string[] = ['Compartido'];
  fechaMinima: string = new Date().toISOString().split('T')[0];



  mostrarSelectorHora: boolean = false;
  mostrarSelectorPlazas: boolean = false;
  hayPlazas: boolean = false;

  formularioReserva = this.fb.group({
    barcoId: this.fb.control<number | null>(null, Validators.required),
    fecha: this.fb.control('', Validators.required),
    hora: this.fb.control('', Validators.required),
    numPersonas: this.fb.control<number | null>(null, Validators.required),
    nombreCliente: this.fb.control('', Validators.required),
    apellidoCliente: this.fb.control('', Validators.required),
    email: this.fb.control('', [Validators.required, Validators.email]),
    telefono: this.fb.control('', [Validators.required, Validators.pattern(/^\+?[0-9]{9,15}$/)]),
    tipoReserva: this.fb.control(null, Validators.required),
    precioTotal: this.fb.control<number | null>({ value: null, disabled: true }, Validators.required)
  });

  ngOnInit() {
    this.barcoService.getBarcos().subscribe((barcos) => {
      this.listaBarcos = barcos;
    });
    console.log(this.listaBarcos);
  }

  cambioTipoReserva() {

  }

  cambioBarco() {
    this.resetFechaHoraPlazas();
  }

  cambioFecha() {
  const fechaSeleccionada = this.formularioReserva.get('fecha')?.value;
  const hoy = new Date().toISOString().split('T')[0];

  if (fechaSeleccionada) {
    if (fechaSeleccionada === hoy) {
      const horaActual = new Date().getHours();
      this.listaHoras = this.horasBase.filter(hora => {
        const [h] = hora.split(':');
        return parseInt(h, 10) > horaActual;
      });
    } else {
      this.listaHoras = [...this.horasBase];
    }

    this.mostrarSelectorHora = true;
    this.formularioReserva.patchValue({ hora: null, numPersonas: null });
    this.mostrarSelectorPlazas = false;
  }
}

  cambioHora() {
    const { barcoId, fecha, hora, tipoReserva } = this.formularioReserva.value;

    if (barcoId && fecha && hora && tipoReserva) {
      const fechaHora = `${fecha}T${hora}`;

      this.reservaService.getInfoPlazas(fechaHora, barcoId).subscribe((respuesta) => {

        const plazasDisponibles = respuesta.plazasDisponibles;

        this.plazasArray = Array.from({ length: plazasDisponibles }, (_, i) => i + 1);

        this.plazasDisponibles = plazasDisponibles;
        this.mostrarSelectorPlazas = true;

        this.formularioReserva.patchValue({
          numPersonas: plazasDisponibles > 0 ? 1 : null
        });
      });
    }
  }

  actualizarPrecio(): void {
    const plazasReservadas = this.formularioReserva.get('numPersonas')?.value ?? 0;

    const precioTotal = plazasReservadas * 15.00;
    this.formularioReserva.patchValue({ precioTotal: precioTotal });

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

       console.log('🟢 Reserva a enviar:', reservaDto);

      this.reservaService.crearReserva(reservaDto).subscribe({
        next: () => {
          alert('Reserva creada correctamente');
          this.formularioReserva.reset();
          this.mostrarSelectorPlazas = false;
          this.mostrarSelectorHora = false;
          this.listaHoras = [];
          this.plazasArray = [];
          this.plazasDisponibles = 0;
        },
        error: (error) => {
          alert('Error al crear la reserva: ' + error.message);
        }
      });
    } else {
      this.formularioReserva.markAllAsTouched();
    }
  }


  private resetFechaHoraPlazas() {
    this.formularioReserva.patchValue({ fecha: null, hora: null, numPersonas: null });
    this.mostrarSelectorHora = false;
    this.mostrarSelectorPlazas = false;
    this.plazasArray = [];
  }
}
