package com.riverBooking.entityDTO;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReservaEntityDTO {

	private Long id;
	
	@NotNull(message = "La fecha de la reserva es obligatoria")
	@FutureOrPresent(message = "La fecha no puede ser anterior a hoy")
	private LocalDateTime fechaReserva;

	@NotBlank(message = "El tipo de reserva es obligatorio")
	private String tipoReserva;

	@Min(value = 1, message = "Debe haber al menos 1 persona")
	@Max(value = 14, message = "El número máximo de personas es 14")
	private int numPersonas;

	@NotBlank(message = "El nombre del cliente es obligatorio")
	private String nombreCliente;

	@NotBlank(message = "Los apellidos del cliente son obligatorios")
	private String apellidoCliente;

	@NotBlank(message = "El teléfono es obligatorio")
	@Pattern(regexp = "^\\+?[0-9]{9,15}$", message = "Introduce un teléfono válido, con o sin prefijo")
	private String telefono;

	@NotBlank(message = "El email es obligatorio")
	@Email(message = "El email no tiene un formato válido")
	private String email;

	@NotBlank(message = "El estado de la reserva es obligatorio")
	private String estado;

	@NotNull(message = "Debe especificarse un barco")
	private Long barcoId;

	@DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
	private BigDecimal precioTotal;

	private String codigoReserva;

	public ReservaEntityDTO() {
	}

	public ReservaEntityDTO(
			Long id,
			@NotNull(message = "La fecha de la reserva es obligatoria") @FutureOrPresent(message = "La fecha no puede ser anterior a hoy") LocalDateTime fechaReserva,
			@NotBlank(message = "El tipo de reserva es obligatorio") String tipoReserva,
			@Min(value = 1, message = "Debe haber al menos 1 persona") int numPersonas,
			@NotBlank(message = "El nombre del cliente es obligatorio") String nombreCliente,
			@NotBlank(message = "Los apellidos del cliente son obligatorios") String apellidoCliente,
			@NotBlank(message = "El teléfono es obligatorio") @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "El teléfono debe tener exactamente 9 dígitos") String telefono,
			@NotBlank(message = "El email es obligatorio") @Email(message = "El email no tiene un formato válido") String email,
			@NotBlank(message = "El estado de la reserva es obligatorio") String estado,
			@NotNull(message = "Debe especificarse un barco") Long barcoId,
			@DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0") BigDecimal precioTotal,
			@NotNull(message = "Debe especificarse un código de reserva.") String codigoReserva) {
		this.id = id;
		this.fechaReserva = fechaReserva;
		this.tipoReserva = tipoReserva;
		this.numPersonas = numPersonas;
		this.nombreCliente = nombreCliente;
		this.apellidoCliente = apellidoCliente;
		this.telefono = telefono;
		this.email = email;
		this.estado = estado;
		this.barcoId = barcoId;
		this.precioTotal = precioTotal;
		this.codigoReserva = codigoReserva;
	}

	public LocalDateTime getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(LocalDateTime fechaReserva) {
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

	public Long getBarcoId() {
		return barcoId;
	}

	public void setBarcoId(Long barcoId) {
		this.barcoId = barcoId;
	}

	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}

	public String getCodigoReserva() {
		return codigoReserva;
	}

	public void setCodigoReserva(String codigoReserva) {
		this.codigoReserva = codigoReserva;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
