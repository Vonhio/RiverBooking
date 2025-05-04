package com.riverBooking.service;

import java.util.List;

import com.riverBooking.entityDTO.ReservaEntityDTO;

public interface ReservaService {
	List<ReservaEntityDTO> getAllReservas();
	ReservaEntityDTO crearReserva(ReservaEntityDTO reservaDto);
	ReservaEntityDTO modificarReserva(String codigoReserva, ReservaEntityDTO reservaDto);
	void eliminarReserva(String codigoReserva);
}
