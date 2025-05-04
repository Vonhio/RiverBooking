package com.riverBooking.mapper;

import com.riverBooking.entity.BarcoEntity;
import com.riverBooking.entityDTO.Barco;

public class BarcoMapper {

	public static Barco toDTO(BarcoEntity barco) {
		Barco dto = new Barco();
        dto.setNombre(barco.getNombre());
        dto.setCapacidad(barco.getCapacidad());
        dto.setDescripcion(barco.getDescripcion());
        return dto;
    }

    public static BarcoEntity toEntity(Barco dto) {
    	BarcoEntity barco = new BarcoEntity();
        barco.setNombre(dto.getNombre());
        barco.setCapacidad(dto.getCapacidad());
        barco.setDescripcion(dto.getDescripcion());
        return barco;
    }
}
