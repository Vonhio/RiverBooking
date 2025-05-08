package com.riverBooking.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ApiError {

	private HttpStatus status;
	private String mensaje;
	private LocalDateTime timestamp;

	public ApiError(HttpStatus status, String mensaje, LocalDateTime timestamp) {
		this.status = status;
		this.mensaje = mensaje;
		this.timestamp = timestamp;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
