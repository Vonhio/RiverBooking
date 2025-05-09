package com.riverBooking.util;

import org.springframework.stereotype.Component;

@Component
public class GeneradorCodigo {

	public String codigoReserva(String nombre, String apellido) {
		String codigoReserva;
		
		nombre = nombre.trim().toUpperCase();
		apellido = apellido.trim().toUpperCase();

		String parteNombre = nombre.length() >= 3 ? nombre.substring(0, 3) : nombre;
		String parteApellido = apellido.length() >= 3 ? apellido.substring(0, 3) : apellido;

		int numero = (int) (Math.random() * 1000);
		String parteNumero = String.format("%03d", numero);

		codigoReserva = parteNombre + parteApellido + parteNumero;

		return codigoReserva;
	}
}
