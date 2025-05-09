package com.riverBooking.service;

import com.riverBooking.entity.ReservaEntity;

public interface MailService {
	void enviarEmail(ReservaEntity reserva);
}
