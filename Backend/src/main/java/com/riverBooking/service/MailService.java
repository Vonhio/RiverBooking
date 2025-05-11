package com.riverBooking.service;

import com.riverBooking.entity.ReservaEntity;

public interface MailService {
	void nuevaReserva(ReservaEntity reserva);
	void modificacionReserva(ReservaEntity reserva);
	void cancelacionReserva(ReservaEntity reserva);
}
