package com.riverBooking.mapper;

import com.riverBooking.entity.BarcoEntity;
import com.riverBooking.entityDTO.BarcoDTO;

public class BarcoMapper {

	public static BarcoDTO toDTO(BarcoEntity barco) {
		BarcoDTO dto = new BarcoDTO();
		dto.setId(barco.getId());
        dto.setNombre(barco.getNombre());
        dto.setCapacidad(barco.getCapacidad());
        dto.setDescripcion(barco.getDescripcion());
        return dto;
    }

    public static BarcoEntity toEntity(BarcoDTO dto) {
    	BarcoEntity barco = new BarcoEntity();
    	barco.setId(dto.getId());
        barco.setNombre(dto.getNombre());
        barco.setCapacidad(dto.getCapacidad());
        barco.setDescripcion(dto.getDescripcion());
        return barco;
    }
}
