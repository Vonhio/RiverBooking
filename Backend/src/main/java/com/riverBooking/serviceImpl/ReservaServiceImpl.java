package com.riverBooking.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.riverBooking.entity.BarcoEntity;
import com.riverBooking.entity.ReservaEntity;
import com.riverBooking.entityDTO.ReservaEntityDTO;
import com.riverBooking.mapper.ReservaMapper;
import com.riverBooking.repository.BarcoRepository;
import com.riverBooking.repository.ReservaRepository;
import com.riverBooking.service.ReservaService;

@Service
public class ReservaServiceImpl implements ReservaService {

	private final ReservaRepository reservaRepository;
	private final BarcoRepository barcoRepository;

	public ReservaServiceImpl(ReservaRepository reservaRepository, BarcoRepository barcoRepository) {
		this.reservaRepository = reservaRepository;
		this.barcoRepository = barcoRepository;
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

		try {
			ReservaEntity reserva = ReservaMapper.toEntity(reservaDto);

			String codigoReserva = generarCodigoReserva(reserva.getNombreCliente(), reserva.getApellidoCliente());
			reserva.setCodigoReserva(codigoReserva);

			BarcoEntity barco = barcoRepository.findById(reservaDto.getBarcoId())
					.orElseThrow(() -> new RuntimeException("No se ha encontrado ningun barco con este ID"));
			reserva.setBarco(barco);

			ReservaEntity reservaGuardada = reservaRepository.save(reserva);
			return ReservaMapper.toDTO(reservaGuardada);

		} catch (Exception e) {
			throw new RuntimeException("Error al crear la reserva.");
		}
	}

	@Override
	public boolean modificarReserva(Long id, ReservaEntityDTO reservaDto) {
		try {
			if(reservaRepository.existsById(id)) {
				ReservaEntity reservaMod = ReservaMapper.toEntity(reservaDto);
				reservaMod.setCodigoReserva(reservaDto.getCodigoReserva());
				
				// Se comprueba si el Barco ha cambiado
				ReservaEntity reserva = reservaRepository.findById(id).orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
				if(reserva.getBarco().getId() != reservaDto.getBarcoId()) {
					BarcoEntity barcoMod = barcoRepository.findById(reservaDto.getBarcoId()).orElseThrow(() -> new RuntimeException("Barco no encontrado"));
					reservaMod.setBarco(barcoMod);
				} else {
					reservaMod.setBarco(reserva.getBarco());
				}
				
				reservaRepository.save(reservaMod);
				return true;
			}
			
			return false;
			
		} catch (Exception e) {
			throw new RuntimeException("Error al modificar la reserva");
		}
	}

	@Override
	public void eliminarReserva(Long id) {
		if (!reservaRepository.existsById(id)) {
			throw new RuntimeException("No existe la reserva seleccionada.");
		}
		reservaRepository.deleteById(id);
	}

	private String generarCodigoReserva(String nombre, String apellido) {
		String codigoReserva;

		do {
			nombre = nombre.trim().toUpperCase();
			apellido = apellido.trim().toUpperCase();

			String parteNombre = nombre.length() >= 3 ? nombre.substring(0, 3) : nombre;
			String parteApellido = apellido.length() >= 3 ? apellido.substring(0, 3) : apellido;

			int numero = (int) (Math.random() * 1000);
			String parteNumero = String.format("%03d", numero);

			codigoReserva = parteNombre + parteApellido + parteNumero;
		} while (reservaRepository.existsByCodigoReserva(codigoReserva));

		return codigoReserva;
	}
}
