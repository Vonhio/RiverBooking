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
  mostrarPlazas: boolean = false;

  plazasArray: number[] = [];
  plazasDisponibles: number = 0;

  /* ────────── formulario reactivo ────────── */
  formularioReserva = this.fb.group({
    barcoId:      this.fb.control<number | null>(null, Validators.required),
    fecha:        this.fb.control('', Validators.required),
    hora:         this.fb.control('', Validators.required),
    numPersonas:  this.fb.control<number | null>(null, Validators.required),

    /* datos personales */
    nombreCliente:     this.fb.control('', Validators.required),
    apellidoCliente:   this.fb.control('', Validators.required),
    email:             this.fb.control('', [Validators.required, Validators.email]),
    telefono:          this.fb.control('', Validators.required)
  });

  /* ────────── ciclo de vida ────────── */
  ngOnInit() {
    this.barcoService.getBarcos().subscribe((barcos) => {
      this.listaBarcos = barcos;
    });
    console.log(this.listaBarcos);
  }

  /* ────────── manejadores de cambios ────────── */
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
  const { barcoId, fecha, hora } = this.formularioReserva.value;

  if (barcoId && fecha && hora) {
    const fechaHora = `${fecha}T${hora}`;

    this.reservaService.getInfoPlazas(fechaHora, barcoId).subscribe((respuesta) => {
      // 👇 respuesta es un único objeto tipo { plazasDisponibles: 5, ... }

      const plazasDisponibles = respuesta.plazasDisponibles;

      // 👇 Creamos un array de números del 1 al plazasDisponibles
      this.plazasArray = Array.from({ length: plazasDisponibles }, (_, i) => i + 1);

      // 👇 Guardamos también el número total, por si lo necesitas
      this.plazasDisponibles = plazasDisponibles;

      // 👇 Marcamos que ya hay datos para mostrar el selector
      this.mostrarPlazas = true;

      // 👇 Valor inicial por defecto en el select si hay mínimo 1 plaza
      this.formularioReserva.patchValue({
        numPersonas: plazasDisponibles > 0 ? 1 : null
      });

      console.log('Plazas disponibles →', plazasDisponibles);
      console.log('Array generado →', this.plazasArray);
    });
  }
}

  /* ────────── envío ────────── */
  enviar(): void {
  }

  /* ────────── util ────────── */
  private resetFechaHoraPlazas() {
    this.formularioReserva.patchValue({ fecha: null, hora: null, numPersonas: null });
    this.mostrarSelectorHora = false;
    this.mostrarPlazas = false;
    this.plazasArray = [];
  }
}
