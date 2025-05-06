package com.riverBooking.mapper;

import com.riverBooking.entity.ReservaEntity;
import com.riverBooking.entityDTO.ReservaEntityDTO;

public class ReservaMapper {

	public static ReservaEntity toEntity(ReservaEntityDTO dto) {
        ReservaEntity reserva = new ReservaEntity();

        reserva.setId(dto.getId());
        reserva.setFechaReserva(dto.getFechaReserva());
        reserva.setTipoReserva(dto.getTipoReserva());
        reserva.setNumPersonas(dto.getNumPersonas());
        reserva.setNombreCliente(dto.getNombreCliente());
        reserva.setApellidoCliente(dto.getApellidoCliente());
        reserva.setTelefono(dto.getTelefono());
        reserva.setEmail(dto.getEmail());
        reserva.setEstado(dto.getEstado());
        reserva.setPrecioTotal(dto.getPrecioTotal());

        return reserva;
    }
	
	public static ReservaEntityDTO toDTO(ReservaEntity reservaEntity) {
		ReservaEntityDTO reservaDTO = new ReservaEntityDTO();
		
		reservaDTO.setId(reservaEntity.getId());
		reservaDTO.setFechaReserva(reservaEntity.getFechaReserva());
        reservaDTO.setTipoReserva(reservaEntity.getTipoReserva());
        reservaDTO.setNumPersonas(reservaEntity.getNumPersonas());
        reservaDTO.setNombreCliente(reservaEntity.getNombreCliente());
        reservaDTO.setApellidoCliente(reservaEntity.getApellidoCliente());
        reservaDTO.setTelefono(reservaEntity.getTelefono());
        reservaDTO.setEmail(reservaEntity.getEmail());
        reservaDTO.setEstado(reservaEntity.getEstado());
        reservaDTO.setBarcoId(reservaEntity.getBarco().getId());
        reservaDTO.setPrecioTotal(reservaEntity.getPrecioTotal());
        reservaDTO.setCodigoReserva(reservaEntity.getCodigoReserva());
		
		return reservaDTO;
	}
}
