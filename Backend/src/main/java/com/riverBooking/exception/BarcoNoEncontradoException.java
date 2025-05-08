package com.riverBooking.exception;

@SuppressWarnings("serial")
public class BarcoNoEncontradoException extends RuntimeException {
	
	public BarcoNoEncontradoException(String mensaje) {
		super(mensaje);
	}

}
