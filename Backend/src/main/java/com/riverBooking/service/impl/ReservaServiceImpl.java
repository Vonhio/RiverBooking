package com.riverBooking.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.riverBooking.entity.BarcoEntity;
import com.riverBooking.entity.ReservaEntity;
import com.riverBooking.entityDTO.InformacionReservasDTO;
import com.riverBooking.entityDTO.ReservaEntityDTO;
import com.riverBooking.exception.BarcoNoEncontradoException;
import com.riverBooking.exception.PlazasInsuficientesException;
import com.riverBooking.exception.ReservaNoEncontradaException;
import com.riverBooking.mapper.ReservaMapper;
import com.riverBooking.repository.BarcoRepository;
import com.riverBooking.repository.ReservaRepository;
import com.riverBooking.service.MailService;
import com.riverBooking.service.ReservaService;
import com.riverBooking.util.GeneradorCodigo;

@Service
public class ReservaServiceImpl implements ReservaService {

	private final ReservaRepository reservaRepository;
	private final BarcoRepository barcoRepository;
	private final GeneradorCodigo generadorCodigo;
	private final MailService mailService;

	public ReservaServiceImpl(ReservaRepository reservaRepository, BarcoRepository barcoRepository,
			GeneradorCodigo generadorCodigo, MailService mailService) {
		this.reservaRepository = reservaRepository;
		this.barcoRepository = barcoRepository;
		this.generadorCodigo = generadorCodigo;
		this.mailService = mailService;
	}

	@Override
	public List<ReservaEntityDTO> getAllReservas() {
		List<ReservaEntity> reservas = reservaRepository.findAll();
		List<ReservaEntityDTO> reservasDTO = reservas.stream().map(reserva -> ReservaMapper.toDTO(reserva))
				.collect(Collectors.toList());
		return reservasDTO;
	}

	@Override
	public ReservaEntityDTO crearReserva(ReservaEntityDTO reservaDto) {

		ReservaEntity reserva = ReservaMapper.toEntity(reservaDto);

		int plazasDisponibles = getInfoReservas(reservaDto.getFechaReserva(), reservaDto.getBarcoId())
				.getPlazasDisponibles();
		boolean hayEspacio = reservaDto.getNumPersonas() <= plazasDisponibles;

		if (!hayEspacio) {
			throw new PlazasInsuficientesException(
					"El numero de plazas reservadas supera el numero de plazas disponibles");
		}

		String codigoReserva;
		do {
			codigoReserva = generadorCodigo.codigoReserva(reservaDto.getNombreCliente(),
					reservaDto.getApellidoCliente());
		} while (reservaRepository.existsByCodigoReserva(codigoReserva));

		reserva.setCodigoReserva(codigoReserva);

		BarcoEntity barco = barcoRepository.findById(reservaDto.getBarcoId())
				.orElseThrow(() -> new BarcoNoEncontradoException(
						"No se ha encontrado ningun barco con este ID: " + reservaDto.getBarcoId()));
		reserva.setBarco(barco);

		ReservaEntity reservaGuardada = reservaRepository.save(reserva);
		mailService.nuevaReserva(reservaGuardada);
		return ReservaMapper.toDTO(reservaGuardada);
	}

	@Override
	public ReservaEntityDTO modificarReserva(Long id, ReservaEntityDTO reservaDto) {

		ReservaEntity reserva = reservaRepository.findById(id)
				.orElseThrow(() -> new ReservaNoEncontradaException("Reserva no encontrada"));

		ReservaEntity reservaMod = ReservaMapper.toEntity(reservaDto);

		validarBarcoYPlazas(reservaDto, reserva, reservaMod);

		reservaMod.setCodigoReserva(reservaDto.getCodigoReserva());
		reservaMod.setId(id);

		reservaRepository.save(reservaMod);
		mailService.modificacionReserva(reservaMod);

		return ReservaMapper.toDTO(reservaMod);
	}

	@Override
	public void eliminarReserva(Long id) {

		ReservaEntity reservaCancelada = reservaRepository.findById(id)
				.orElseThrow(() -> new ReservaNoEncontradaException("No existe la reserva seleccionada."));

		reservaRepository.deleteById(id);
		mailService.cancelacionReserva(reservaCancelada);
	}

	@Override
	public InformacionReservasDTO getInfoReservas(LocalDateTime fechaHora, Long barcoId) {

		BarcoEntity barco = barcoRepository.findById(barcoId)
				.orElseThrow(() -> new BarcoNoEncontradoException("Barco no encontrado"));

		int plazasTotales = barco.getCapacidad();
		int plazasOcupadas = reservaRepository.numeroPlazasReservadas(fechaHora, barcoId);
		int plazasLibres = plazasTotales - plazasOcupadas;

		InformacionReservasDTO infoPlazas = new InformacionReservasDTO(plazasTotales, plazasOcupadas, plazasLibres);
		return infoPlazas;
	}

	public void validarBarcoYPlazas(ReservaEntityDTO reservaDto, ReservaEntity reserva, ReservaEntity reservaMod) {

		int plazasDisponibles = getInfoReservas(reservaDto.getFechaReserva(), reservaDto.getBarcoId())
				.getPlazasDisponibles();
		int plazasAñadidas = reservaDto.getNumPersonas() - reserva.getNumPersonas();
		boolean mismoBarco = reserva.getBarco().getId().equals(reservaDto.getBarcoId());

		if (!mismoBarco && reservaDto.getNumPersonas() <= plazasDisponibles) {
			BarcoEntity barcoMod = barcoRepository.findById(reservaDto.getBarcoId())
					.orElseThrow(() -> new BarcoNoEncontradoException("Barco no encontrado"));
			reservaMod.setBarco(barcoMod);
		} else if (mismoBarco && (plazasAñadidas < 0 || plazasAñadidas <= plazasDisponibles)) {
			reservaMod.setBarco(reserva.getBarco());
		} else {
			throw new PlazasInsuficientesException(
					"El numero de plazas a modificar es superior a las plazas disponibles");
		}
	}
}
