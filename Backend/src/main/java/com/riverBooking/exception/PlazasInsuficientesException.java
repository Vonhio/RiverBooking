package com.riverBooking.exception;

@SuppressWarnings("serial")
public class PlazasInsuficientesException extends RuntimeException {
	
	public PlazasInsuficientesException(String mensaje) {
		super(mensaje);
	}

}
