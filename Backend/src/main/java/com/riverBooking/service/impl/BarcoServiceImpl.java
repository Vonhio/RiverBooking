package com.riverBooking.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.riverBooking.entity.BarcoEntity;
import com.riverBooking.entityDTO.BarcoEntityDTO;
import com.riverBooking.mapper.BarcoMapper;
import com.riverBooking.repository.BarcoRepository;
import com.riverBooking.service.BarcoService;

@Service
public class BarcoServiceImpl implements BarcoService {

	private final BarcoRepository barcoRepository;

	public BarcoServiceImpl(BarcoRepository barcoRepository) {
		this.barcoRepository = barcoRepository;
	}

	@Override
	public List<BarcoEntityDTO> getAllBarcos() {
		List<BarcoEntity> barcos = barcoRepository.findAll();
		List<BarcoEntityDTO> barcosDto = barcos.stream().map(barco -> BarcoMapper.toDTO(barco))
				.collect(Collectors.toList());
		return barcosDto;
	}
}
