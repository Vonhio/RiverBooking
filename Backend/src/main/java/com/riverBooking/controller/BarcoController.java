package com.riverBooking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riverBooking.entityDTO.BarcoEntityDTO;
import com.riverBooking.service.BarcoService;

@RestController
@RequestMapping("/tipos")
public class BarcoController {

	private final BarcoService barcoService;

	public BarcoController(BarcoService barcoService) {
		this.barcoService = barcoService;
	}

	// Devuelve un JSON con todos los tipos de barcos existentes
	@GetMapping("/barcos")
	public List<BarcoEntityDTO> getAllBarcos() {
		return barcoService.getAllBarcos();
	}
}
