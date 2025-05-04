package com.riverBooking.mapper;

import com.riverBooking.entity.ReservaEntity;
import com.riverBooking.entityDTO.BarcoEntityDTO;
import com.riverBooking.entityDTO.ReservaEntityDTO;

public class ReservaMapper {

	public static ReservaEntity toEntity(ReservaEntityDTO dto, BarcoEntityDTO barco) {
        ReservaEntity reserva = new ReservaEntity();

        reserva.setFechaReserva(dto.getFechaReserva());
        reserva.setTipoReserva(dto.getTipoReserva());
        reserva.setNumPersonas(dto.getNumPersonas());
        reserva.setNombreCliente(dto.getNombreCliente());
        reserva.setApellidoCliente(dto.getApellidoCliente());
        reserva.setTelefono(dto.getTelefono());
        reserva.setEmail(dto.getEmail());
        reserva.setEstado(dto.getEstado());
        reserva.setBarco(BarcoMapper.toEntity(barco));
        reserva.setPrecioTotal(dto.getPrecioTotal());

        return reserva;
    }
	
	public static ReservaEntityDTO toDTO(ReservaEntity reservaEntity) {
		ReservaEntityDTO reservaDTO = new ReservaEntityDTO();
		
		reservaDTO.setFechaReserva(reservaEntity.getFechaReserva());
        reservaDTO.setTipoReserva(reservaEntity.getTipoReserva());
        reservaDTO.setNumPersonas(reservaEntity.getNumPersonas());
        reservaDTO.setNombreCliente(reservaEntity.getNombreCliente());
        reservaDTO.setApellidoCliente(reservaEntity.getApellidoCliente());
        reservaDTO.setTelefono(reservaEntity.getTelefono());
        reservaDTO.setEmail(reservaEntity.getEmail());
        reservaDTO.setEstado(reservaEntity.getEstado());
        reservaDTO.setBarco(BarcoMapper.toDTO(reservaEntity.getBarco()));
        reservaDTO.setPrecioTotal(reservaEntity.getPrecioTotal());
		
		return reservaDTO;
	}
}
