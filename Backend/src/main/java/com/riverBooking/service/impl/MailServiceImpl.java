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
	public void nuevaReserva(ReservaEntity reserva) {
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

	@Override
	public void modificacionReserva(ReservaEntity reserva) {
		SimpleMailMessage mensaje = new SimpleMailMessage();
		mensaje.setTo(reserva.getEmail());
		mensaje.setSubject("Reserva modificada - River Booking");
		mensaje.setText("Hola " + reserva.getNombreCliente() + " " + reserva.getApellidoCliente() + ",\n\n" +
		         "Te informamos que los datos de tu reserva han sido modificados correctamente.\n" +
		         "Detalles actualizados de la reserva:\n" +
		         "Fecha y hora: " + reserva.getFechaReserva() + "\n" +
		         "Cantidad de personas: " + reserva.getNumPersonas() + "\n\n" +
		         "Tu código de reserva sigue siendo: " + reserva.getCodigoReserva() + "\n" +
		         "Si tienes cualquier duda o deseas realizar más cambios, no dudes en contactarnos.\n\n" +
		         "¡Gracias por confiar en River Booking!");

	     mailSender.send(mensaje);
		}

	@Override
	public void cancelacionReserva(ReservaEntity reserva) {
		SimpleMailMessage mensaje = new SimpleMailMessage();
		mensaje.setTo(reserva.getEmail());
		mensaje.setSubject("Reserva cancelada - River Booking");
		mensaje.setText("Hola " + reserva.getNombreCliente() + " " + reserva.getApellidoCliente() + ",\n\n" +
		         "Lamentamos informarte que tu reserva con código " + reserva.getCodigoReserva() + " ha sido cancelada.\n\n" +
		         "Si esta cancelación fue un error o deseas realizar una nueva reserva, no dudes en ponerte en contacto con nosotros o visitar nuestra web.\n\n" +
		         "Gracias por tu interés en River Booking. Esperamos verte pronto.");
		mailSender.send(mensaje);

		
	}
	}
