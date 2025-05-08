package com.riverBooking.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riverBooking.entityDTO.ReservaEntityDTO;
import com.riverBooking.service.ReservaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

	private final ReservaService reservaService;

	public ReservaController(ReservaService reservaService) {
		this.reservaService = reservaService;
	}

	@GetMapping
	public ResponseEntity<?> getAllReservas() {
		try {
			return ResponseEntity.ok(reservaService.getAllReservas());
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PostMapping("/guardar")
	public ResponseEntity<?> guardarReserva(@RequestBody @Valid ReservaEntityDTO reservaEntityDto) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.crearReserva(reservaEntityDto));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping("/modificar/{id}")
	public ResponseEntity<?> modificarReserva(@PathVariable Long id,
			@RequestBody @Valid ReservaEntityDTO reservaEntityDto) {
		try {
			return ResponseEntity.ok(reservaService.modificarReserva(id, reservaEntityDto));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarReserva(@PathVariable Long id) {
		try {
			reservaService.eliminarReserva(id);
			return ResponseEntity.noContent().build();
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/plazas")
	public ResponseEntity<?> infoPlazas(@RequestParam LocalDateTime fechaHora, @RequestParam Long barcoId) {
		try {
			return ResponseEntity.ok(reservaService.getInfoReservas(fechaHora, barcoId));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
