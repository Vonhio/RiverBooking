package com.riverBooking.entityDTO;

public class InformacionReservasDTO {

	private int capacidadTotal;
	private int plazasReservadas;
	private int plazasDisponibles;

	public InformacionReservasDTO() {
	}

	public InformacionReservasDTO(int capacidadTotal, int plazasReservadas, int plazasDisponibles) {
		this.capacidadTotal = capacidadTotal;
		this.plazasReservadas = plazasReservadas;
		this.plazasDisponibles = plazasDisponibles;
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
	
	

}
