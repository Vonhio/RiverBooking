package com.riverBooking.service;

import java.util.List;

import com.riverBooking.entityDTO.ReservaEntityDTO;

public interface ReservaService {
	List<ReservaEntityDTO> getAllReservas();
	ReservaEntityDTO crearReserva(ReservaEntityDTO reservaDto);
	boolean modificarReserva(Long Id, ReservaEntityDTO reservaDto);
	void eliminarReserva(Long Id);
}
