package com.riverBooking.mapper;

import com.riverBooking.entity.BarcoEntity;
import com.riverBooking.entityDTO.BarcoEntityDTO;

public class BarcoMapper {

	public static BarcoEntityDTO toDTO(BarcoEntity barco) {
		BarcoEntityDTO dto = new BarcoEntityDTO();
		dto.setId(barco.getId());
        dto.setNombre(barco.getNombre());
        dto.setCapacidad(barco.getCapacidad());
        dto.setDescripcion(barco.getDescripcion());
        return dto;
    }

    public static BarcoEntity toEntity(BarcoEntityDTO dto) {
    	BarcoEntity barco = new BarcoEntity();
    	barco.setId(dto.getId());
        barco.setNombre(dto.getNombre());
        barco.setCapacidad(dto.getCapacidad());
        barco.setDescripcion(dto.getDescripcion());
        return barco;
    }
}
