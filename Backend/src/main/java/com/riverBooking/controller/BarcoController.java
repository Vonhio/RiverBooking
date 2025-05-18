package com.riverBooking.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riverBooking.entityDTO.BarcoEntityDTO;
import com.riverBooking.service.BarcoService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/barcos")
public class BarcoController {

	private final BarcoService barcoService;

	public BarcoController(BarcoService barcoService) {
		this.barcoService = barcoService;
	}

	// Devuelve un JSON con todos los tipos de barcos existentes
	@GetMapping
	public ResponseEntity<List<BarcoEntityDTO>> getAllBarcos() {
			return ResponseEntity.ok(barcoService.getAllBarcos());
	}
}
