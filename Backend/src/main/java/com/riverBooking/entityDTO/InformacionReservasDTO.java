package com.riverBooking.entityDTO;

public class InformacionReservasDTO {

	private int capacidadTotal;
	private int plazasReservadas;
	private int plazasDisponibles;
	private boolean esPrivado;

	public InformacionReservasDTO() {
	}

	public InformacionReservasDTO(int capacidadTotal, int plazasReservadas, int plazasDisponibles, boolean esPrivado) {
		this.capacidadTotal = capacidadTotal;
		this.plazasReservadas = plazasReservadas;
		this.plazasDisponibles = plazasDisponibles;
		this.esPrivado = esPrivado;

	}

	public int getCapacidadTotal() {
		return capacidadTotal;
	}

	public void setCapacidadTotal(int capacidadTotal) {
		this.capacidadTotal = capacidadTotal;
	}

	public int getPlazasReservadas() {
		return plazasReservadas;
	}

	public void setPlazasReservadas(int plazasReservadas) {
		this.plazasReservadas = plazasReservadas;
	}

	public int getPlazasDisponibles() {
		return plazasDisponibles;
	}

	public void setPlazasDisponibles(int plazasDisponibles) {
		this.plazasDisponibles = plazasDisponibles;
	}

	public boolean isEsPrivado() {
		return esPrivado;
	}

	public void setEsPrivado(boolean esPrivado) {
		this.esPrivado = esPrivado;
	}

}
