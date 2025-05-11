package com.riverBooking.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.riverBooking.entity.ReservaEntity;
import com.riverBooking.service.MailService;

@Service
public class MailServiceImpl implements MailService {
	
	private final JavaMailSender mailSender;
	
	public MailServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void enviarEmail(ReservaEntity reserva) {
     SimpleMailMessage mensaje = new SimpleMailMessage();
     mensaje.setTo(reserva.getEmail());
     mensaje.setSubject("Reserva confirmada - River Booking");
     mensaje.setText("Hola " + reserva.getNombreCliente() + " " + reserva.getApellidoCliente() + ",\n\n" +
			 "Tu reserva ha sido confirmada.\n" +
			 "Detalles de la reserva:\n" +
			 "Fecha y hora: " + reserva.getFechaReserva() + "\n" +
			 "Cantidad de personas: " + reserva.getNumPersonas() + "\n\n" +
			 "Su codigo de reserva es: " + reserva.getCodigoReserva() + "\n" +
			 "Si desea modificar o cancelar su reserva, por favor contáctenos.\n\n" +
			 "¡Gracias por elegirnos!");
     
     mailSender.send(mensaje);
	}
	
}
