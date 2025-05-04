package com.riverBooking.entityDTO;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ReservaEntityDTO {

	@NotNull(message = "La fecha de la reserva es obligatoria")
	@FutureOrPresent(message = "La fecha no puede ser anterior a hoy")
	private LocalDate fechaReserva;

	@NotBlank(message = "El tipo de reserva es obligatorio")
	private String tipoReserva;

	@Min(value = 1, message = "Debe haber al menos 1 persona")
	private int numPersonas;

	@NotBlank(message = "El nombre del cliente es obligatorio")
	private String nombreCliente;

	@NotBlank(message = "Los apellidos del cliente son obligatorios")
	private String apellidoCliente;

	@NotBlank(message = "El teléfono es obligatorio")
	@Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "El teléfono debe tener exactamente 9 dígitos")
	private String telefono;

	@NotBlank(message = "El email es obligatorio")
	@Email(message = "El email no tiene un formato válido")
	private String email;

	@NotBlank(message = "El estado de la reserva es obligatorio")
	private String estado;

	@NotNull(message = "Debe especificarse un barco")
	private BarcoEntityDTO barco;

	@DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
	private BigDecimal precioTotal;

	public ReservaEntityDTO() {
	}

	public ReservaEntityDTO(
			@NotNull(message = "La fecha de la reserva es obligatoria") @FutureOrPresent(message = "La fecha no puede ser anterior a hoy") LocalDate fechaReserva,
			@NotBlank(message = "El tipo de reserva es obligatorio") String tipoReserva,
			@Min(value = 1, message = "Debe haber al menos 1 persona") int numPersonas,
			@NotBlank(message = "El nombre del cliente es obligatorio") String nombreCliente,
			@NotBlank(message = "Los apellidos del cliente son obligatorios") String apellidoCliente,
			@NotBlank(message = "El teléfono es obligatorio") @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "El teléfono debe tener exactamente 9 dígitos") String telefono,
			@NotBlank(message = "El email es obligatorio") @Email(message = "El email no tiene un formato válido") String email,
			@NotBlank(message = "El estado de la reserva es obligatorio") String estado,
			@NotNull(message = "Debe especificarse un barco") BarcoEntityDTO barco,
			@DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0") BigDecimal precioTotal) {
		this.fechaReserva = fechaReserva;
		this.tipoReserva = tipoReserva;
		this.numPersonas = numPersonas;
		this.nombreCliente = nombreCliente;
		this.apellidoCliente = apellidoCliente;
		this.telefono = telefono;
		this.email = email;
		this.estado = estado;
		this.barco = barco;
		this.precioTotal = precioTotal;
	}

	public LocalDate getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(LocalDate fechaReserva) {
		this.fechaReserva = fechaReserva;
	}

	public String getTipoReserva() {
		return tipoReserva;
	}

	public void setTipoReserva(String tipoReserva) {
		this.tipoReserva = tipoReserva;
	}

	public int getNumPersonas() {
		return numPersonas;
	}

	public void setNumPersonas(int numPersonas) {
		this.numPersonas = numPersonas;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getApellidoCliente() {
		return apellidoCliente;
	}

	public void setApellidoCliente(String apellidoCliente) {
		this.apellidoCliente = apellidoCliente;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public BarcoEntityDTO getBarco() {
		return barco;
	}

	public void setBarco(BarcoEntityDTO barco) {
		this.barco = barco;
	}

	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}
}
