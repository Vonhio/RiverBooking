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
import com.riverBooking.exception.ReservaNoEncontradaException;
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
	void crearReserva_creacionCorrecta() {
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
		verify(mailService).nuevaReserva(any());
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

	@Test
	void eliminarReserva_ReservaNoEncontrada() {
		// Arrange
		Long idReserva = 1L;

		// Mocks
		when(reservaRepository.existsById(idReserva)).thenReturn(false);

		// Act & Assert
		assertThrows(ReservaNoEncontradaException.class, () -> {
			reservaServiceImpl.eliminarReserva(idReserva);
		});
		verify(reservaRepository, never()).deleteById(idReserva);
	}

	@Test
	void eliminarReserva_ReservaEncontrada() {
		// Arrange
		Long idReserva = 1L;

		// Mocks
		when(reservaRepository.existsById(idReserva)).thenReturn(true);

		// Act & Assert
		reservaServiceImpl.eliminarReserva(idReserva);
		verify(reservaRepository).deleteById(idReserva);
	}

	@Test
	void modificarReserva_modificacionCorrecta() {

		// Arrange
		Long idReserva = 1L;
		LocalDateTime fecha = LocalDateTime.now();

		ReservaEntityDTO dto = new ReservaEntityDTO();
		dto.setNumPersonas(9);
		dto.setBarcoId(1L);
		dto.setFechaReserva(fecha);

		BarcoEntity barco = new BarcoEntity();
		barco.setId(1L);
		barco.setCapacidad(10);

		ReservaEntity reservaExistente = new ReservaEntity();
		reservaExistente.setNumPersonas(6);
		reservaExistente.setBarco(barco);
		reservaExistente.setFechaReserva(fecha);

		ReservaEntity reservaModificada = new ReservaEntity();
		reservaModificada.setNumPersonas(9);
		reservaModificada.setBarco(barco);
		reservaModificada.setFechaReserva(fecha);

		// Mocks
		when(reservaRepository.findById(idReserva)).thenReturn(Optional.of(reservaExistente));
		when(barcoRepository.findById(dto.getBarcoId())).thenReturn(Optional.of(barco));
		when(reservaRepository.numeroPlazasReservadas(any(), eq(reservaExistente.getBarco().getId()))).thenReturn(6);
		when(reservaRepository.save(any())).thenReturn(reservaModificada);

		// Act
		ReservaEntityDTO resultado = reservaServiceImpl.modificarReserva(idReserva, dto);

		// Asserts
		assertNotNull(resultado);
		assertEquals(9, resultado.getNumPersonas());
		assertEquals(1L, resultado.getBarcoId());
		verify(reservaRepository).save(any());
	}

	@Test
	void modificarReserva_LanzarExcepcionCuandoBarcoNoExiste() {

		// Arrange
		Long idReserva = 1L;

		ReservaEntityDTO dto = new ReservaEntityDTO();
		dto.setNumPersonas(9);
		dto.setBarcoId(1L);

		ReservaEntity reservaExistente = new ReservaEntity();
		reservaExistente.setNumPersonas(6);

		// Mocks
		when(reservaRepository.findById(idReserva)).thenReturn(Optional.of(reservaExistente));
		when(barcoRepository.findById(dto.getBarcoId())).thenReturn(Optional.empty());

		// Asserts
		assertThrows(BarcoNoEncontradoException.class, () -> {
			reservaServiceImpl.modificarReserva(idReserva, dto);
		});
		verify(reservaRepository, never()).save(any());
	}

	@Test
	void modificarReserva_LanzarExcepcionCuandoPlazasInsuficientes() {

		// Arrange
		Long idReserva = 1L;
		LocalDateTime fecha = LocalDateTime.now();

		ReservaEntityDTO dto = new ReservaEntityDTO();
		dto.setNumPersonas(12);
		dto.setBarcoId(1L);
		dto.setFechaReserva(fecha);

		BarcoEntity barco = new BarcoEntity();
		barco.setId(1L);
		barco.setCapacidad(10);

		ReservaEntity reservaExistente = new ReservaEntity();
		reservaExistente.setNumPersonas(6);
		reservaExistente.setBarco(barco);
		reservaExistente.setFechaReserva(fecha);

		ReservaEntity reservaModificada = new ReservaEntity();
		reservaModificada.setNumPersonas(12);
		reservaModificada.setBarco(barco);
		reservaModificada.setFechaReserva(fecha);

		// Mocks
		when(reservaRepository.findById(idReserva)).thenReturn(Optional.of(reservaExistente));
		when(barcoRepository.findById(dto.getBarcoId())).thenReturn(Optional.of(barco));
		when(reservaRepository.numeroPlazasReservadas(any(), eq(reservaExistente.getBarco().getId()))).thenReturn(10);

		// Act & Asserts
		assertThrows(PlazasInsuficientesException.class, () -> {
			reservaServiceImpl.modificarReserva(idReserva, dto);
		});
		verify(reservaRepository, never()).save(any());
	}
}
