package com.riverBooking.util;

import java.text.Normalizer;

import org.springframework.stereotype.Component;

@Component
public class GeneradorCodigo {

	public String codigoReserva(String nombre, String apellido) {
		String codigoReserva;
		
		nombre = Normalizer.normalize(nombre.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toUpperCase();

        apellido = Normalizer.normalize(apellido.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toUpperCase();

		String parteNombre = nombre.length() >= 3 ? nombre.substring(0, 3) : nombre;
		String parteApellido = apellido.length() >= 3 ? apellido.substring(0, 3) : apellido;

		int numero = (int) (Math.random() * 1000);
		String parteNumero = String.format("%03d", numero);

		codigoReserva = parteNombre + parteApellido + parteNumero;

		return codigoReserva;
	}
}