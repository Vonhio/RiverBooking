package com.riverBooking.exception;

@SuppressWarnings("serial")
public class ReservaNoEncontradaException extends RuntimeException {

	public ReservaNoEncontradaException(String mensaje) {
		super(mensaje);
	}
}
