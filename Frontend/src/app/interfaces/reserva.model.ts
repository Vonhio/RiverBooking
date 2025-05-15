export interface Reserva {
  id?: number;
  barcoId: number;
  fechaReserva: string;
  numPersonas: number;
  nombreCliente: string;
  apellidoCliente: string;
  email: string;
  telefono: string;
  estado: string;
  tipoReserva: string;
  precioTotal: number;
  codigoReserva?: string;
}
