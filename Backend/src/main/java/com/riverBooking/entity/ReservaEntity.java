package com.riverBooking.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "reserva")
public class ReservaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fecha_reserva", nullable = false)
	private LocalDate fechaReserva;

	@Column(name = "tipo_reserva", nullable = false)
	private String tipoReserva;

	@Column(name = "num_personas", nullable = false)
	private int numPersonas;

	@Column(name = "nombre_cliente", nullable = false)
	private String nombreCliente;

	@Column(name = "apellido_cliente", nullable = false)
	private String apellidoCliente;

	@Column(name = "telefono", nullable = false)
	private String telefono;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "estado", nullable = false)
	private String estado;

	@ManyToOne
	@JoinColumn(name = "barco_id", nullable = false)
	private BarcoEntity barco;

	@Column(name = "precio_total", nullable = false)
	private BigDecimal precioTotal;

	@Column(name = "codigo_reserva", nullable = false, unique = true)
	private String codigoReserva;

	public ReservaEntity() {
	}

	public ReservaEntity(Long id, LocalDate fechaReserva, String tipoReserva, int numPersonas, String nombreCliente,
			String apellidoCliente, String telefono, String email, String estado, BarcoEntity tipoBarco,
			BigDecimal precioTotal, String codigoReserva) {
		this.id = id;
		this.fechaReserva = fechaReserva;
		this.tipoReserva = tipoReserva;
		this.numPersonas = numPersonas;
		this.nombreCliente = nombreCliente;
		this.apellidoCliente = apellidoCliente;
		this.telefono = telefono;
		this.email = email;
		this.estado = estado;
		this.barco = tipoBarco;
		this.precioTotal = precioTotal;
		this.codigoReserva = codigoReserva;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public BarcoEntity getBarco() {
		return barco;
	}

	public void setBarco(BarcoEntity barco) {
		this.barco = barco;
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

}
