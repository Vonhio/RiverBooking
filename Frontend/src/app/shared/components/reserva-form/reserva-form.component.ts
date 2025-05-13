import { Component, inject } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { BarcoService } from '../../../core/services/barco/barco.service';
import { Plazas } from '../../../interfaces/plazas.model';
import { ReservaService } from '../../../core/services/reserva/reserva.service';
import { Barco } from '../../../interfaces/barco.model';

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

  /* ────────── inyecciones ────────── */
  private fb = inject(FormBuilder);
  private http = inject(HttpClient);
  private barcoService = inject(BarcoService);
  private reservaService = inject(ReservaService);

  /* ────────── datos de apoyo ────────── */
  listaBarcos: Barco[] = [];
  horasBase: string[] = ['10:00', '12:00', '14:00', '16:00', '18:00'];
  listaHoras: string[] = [];
  mostrarSelectorHora: boolean = false;
  mostrarSelectorPlazas: boolean = false;

  plazasArray: number[] = [];
  plazasDisponibles: number = 0;
  hayPlazas: boolean = false;

  formularioReserva = this.fb.group({
    barcoId: this.fb.control<number | null>(null, Validators.required),
    fecha: this.fb.control('', Validators.required),
    hora: this.fb.control('', Validators.required),
    numPersonas: this.fb.control<number | null>(null, Validators.required),
    nombreCliente: this.fb.control('', Validators.required),
    apellidoCliente: this.fb.control('', Validators.required),
    email: this.fb.control('', [Validators.required, Validators.email]),
    telefono: this.fb.control('', Validators.required),
    tipoReserva: this.fb.control<'Pública' | 'Privada'>('Pública', Validators.required),
    precioTotal: this.fb.control<number | null>(null, Validators.required)
  });

  ngOnInit() {
    this.barcoService.getBarcos().subscribe((barcos) => {
      this.listaBarcos = barcos;
    });
    console.log(this.listaBarcos);
  }

  cambioBarco() {
    this.resetFechaHoraPlazas();
  }

  cambioFecha() {
    if (this.formularioReserva.get('barcoId')?.value) {
      this.listaHoras = this.horasBase;
      this.mostrarSelectorHora = true;
      this.formularioReserva.patchValue({ hora: null, numPersonas: null });
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

  actualizarPrecio(): void{
    const plazasReservadas = this.formularioReserva.get('numPersonas')?.value ?? 0;
    const tipoReserva = this.formularioReserva.get('tipoReserva')?.value;


    if(tipoReserva === 'Privada'){
      const precioTotal = 150.00;
      this.formularioReserva.patchValue({ precioTotal: precioTotal });
    } else {
      const precioTotal = plazasReservadas * 15.00;
      this.formularioReserva.patchValue({ precioTotal: precioTotal });
    }
  }

  enviar(): void {
  }

  private resetFechaHoraPlazas() {
    this.formularioReserva.patchValue({ fecha: null, hora: null, numPersonas: null });
    this.mostrarSelectorHora = false;
    this.mostrarSelectorPlazas = false;
    this.plazasArray = [];
  }

}
