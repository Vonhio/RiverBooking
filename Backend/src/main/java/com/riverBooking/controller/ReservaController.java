package com.riverBooking.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riverBooking.entityDTO.InformacionReservasDTO;
import com.riverBooking.entityDTO.ReservaEntityDTO;
import com.riverBooking.service.ReservaService;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/reservas")
public class ReservaController {

	private final ReservaService reservaService;

	public ReservaController(ReservaService reservaService) {
		this.reservaService = reservaService;
	}

	@GetMapping
	public ResponseEntity<List<ReservaEntityDTO>> getAllReservas() {
		return ResponseEntity.ok(reservaService.getAllReservas());
	}

	@PostMapping("/guardar")
	public ResponseEntity<ReservaEntityDTO> guardarReserva(@RequestBody @Valid ReservaEntityDTO reservaEntityDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.crearReserva(reservaEntityDto));

	}

	@PutMapping("/modificar")
	public ResponseEntity<ReservaEntityDTO> modificarReserva(@RequestBody @Valid ReservaEntityDTO reservaEntityDto) {
		return ResponseEntity.ok(reservaService.modificarReserva(reservaEntityDto));
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
		reservaService.eliminarReserva(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/plazas")
	public ResponseEntity<InformacionReservasDTO> infoPlazas(@RequestParam LocalDateTime fechaHora,
			@RequestParam Long barcoId) {
		return ResponseEntity.ok(reservaService.getInfoReservas(fechaHora, barcoId));

	}
}
