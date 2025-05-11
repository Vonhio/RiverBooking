package com.riverBooking.service.impl;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.riverBooking.entity.BarcoEntity;
import com.riverBooking.entity.ReservaEntity;
import com.riverBooking.entityDTO.ReservaEntityDTO;
import com.riverBooking.exception.BarcoNoEncontradoException;
import com.riverBooking.exception.PlazasInsuficientesException;
import com.riverBooking.repository.BarcoRepository;
import com.riverBooking.repository.ReservaRepository;
import com.riverBooking.service.MailService;
import com.riverBooking.util.GeneradorCodigo;

public class ReservaServiceImplTest {

	@Mock
	private ReservaRepository reservaRepository;

	@Mock
	private BarcoRepository barcoRepository;

	@Mock
	private GeneradorCodigo generadorCodigo;

	@InjectMocks
	private ReservaServiceImpl reservaServiceImpl;

	@Mock
	private MailService mailService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void crearReserva_plazasYBarcoDisponibles() {
		// Arrange
		ReservaEntityDTO dto = new ReservaEntityDTO();
		dto.setNumPersonas(3);
		dto.setBarcoId(1L);
		dto.setFechaReserva(LocalDateTime.now());
		dto.setNombreCliente("Antonio");
		dto.setApellidoCliente("Pérez");

		BarcoEntity barco = new BarcoEntity();
		barco.setId(1L);
		barco.setCapacidad(10);

		ReservaEntity reserva = new ReservaEntity();
		reserva.setNumPersonas(3);
		reserva.setBarco(barco);

		ReservaEntity reservaGuardada = new ReservaEntity();
		reservaGuardada.setCodigoReserva("ABC123");
		reservaGuardada.setNumPersonas(3);
		reservaGuardada.setBarco(barco);

		// Mocks
		when(barcoRepository.findById(1L)).thenReturn(Optional.of(barco));
		when(reservaRepository.numeroPlazasReservadas(any(), eq(1L))).thenReturn(5);
		when(generadorCodigo.codigoReserva("Antonio", "Pérez")).thenReturn("ABC123");
		when(reservaRepository.existsByCodigoReserva("ABC123")).thenReturn(false);
		when(reservaRepository.save(any())).thenReturn(reservaGuardada);

		// Act
		ReservaEntityDTO resultado = reservaServiceImpl.crearReserva(dto);

		// Assert
		assertNotNull(resultado);
		assertEquals("ABC123", resultado.getCodigoReserva());
		assertEquals(3, resultado.getNumPersonas());
		verify(reservaRepository).save(any());
		verify(mailService).enviarEmail(any());
	}

	@Test
	void crearReserva_LanzarExcepcionCuandoNoHayPlazasDisponibles() {
		// Arrange
		ReservaEntityDTO dto = new ReservaEntityDTO();
		dto.setNumPersonas(6);
		dto.setBarcoId(1L);
		dto.setFechaReserva(LocalDateTime.now());
		dto.setNombreCliente("Ana");
		dto.setApellidoCliente("López");

		BarcoEntity barco = new BarcoEntity();
		barco.setId(1L);
		barco.setCapacidad(5);

		// Mocks
		when(barcoRepository.findById(1L)).thenReturn(Optional.of(barco));
		when(reservaRepository.numeroPlazasReservadas(any(), eq(1L))).thenReturn(5);

		// Act & Assert
		assertThrows(PlazasInsuficientesException.class, () -> {
			reservaServiceImpl.crearReserva(dto);
		});
	}

	@Test
	void crearReserva_LanzarExcepcionCuandoBarcoNoExiste() {
		// Arrange
		ReservaEntityDTO dto = new ReservaEntityDTO();
		dto.setNumPersonas(3);
		dto.setBarcoId(3L);
		dto.setFechaReserva(LocalDateTime.now());
		dto.setNombreCliente("Antonio");
		dto.setApellidoCliente("Pérez");

		BarcoEntity barco = new BarcoEntity();
		barco.setId(1L);
		barco.setCapacidad(10);

		// Mocks
		when(barcoRepository.findById(3L)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(BarcoNoEncontradoException.class, () -> {
			reservaServiceImpl.crearReserva(dto);
		});
	}

}
