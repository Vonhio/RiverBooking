package com.riverBooking.entityDTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class BarcoDTO {

	private Long id;
	
	@NotBlank(message = "El nombre del barco no puede estar vacío")
	private String nombre;

	@Min(value = 1, message = "Debe haber al menos una plaza")
	@Max(value = 14, message = "El barco no puede superar las 14 plazas")
	private int capacidad;

	private String descripcion;

	public BarcoDTO(Long id, String nombre, int capacidad, String descripcion) {
		this.id = id;
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.descripcion = descripcion;
	}

	public BarcoDTO() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
