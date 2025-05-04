package com.riverBooking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riverBooking.entityDTO.ReservaEntityDTO;
import com.riverBooking.service.ReservaService;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

	private final ReservaService reservaService;
	
	public ReservaController(ReservaService reservaService) {
		this.reservaService = reservaService;
	}
	
	@GetMapping("/lista")
	public List<ReservaEntityDTO> getAllReservas(){
		return reservaService.getAllReservas();
	}
}
