package com.riverBooking.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.riverBooking.entity.ReservaEntity;
import com.riverBooking.entityDTO.ReservaEntityDTO;
import com.riverBooking.mapper.ReservaMapper;
import com.riverBooking.repository.ReservaRepository;
import com.riverBooking.service.ReservaService;

@Service
public class ReservaServiceImpl implements ReservaService {

	private final ReservaRepository reservaRepository;
	
	public ReservaServiceImpl(ReservaRepository reservaRepository) {
		this.reservaRepository = reservaRepository;
	}

	@Override
	public List<ReservaEntityDTO> getAllReservas() {
		List<ReservaEntity> reservas = reservaRepository.findAll();
		List<ReservaEntityDTO> reservasDTO = reservas.stream().map(reserva -> ReservaMapper.toDTO(reserva)).collect(Collectors.toList());
		return reservasDTO;
	}

	@Override
	public ReservaEntityDTO crearReserva(ReservaEntityDTO reservaDto) {
		ReservaEntity reserva = ReservaMapper.toEntity(reservaDto, reservaDto.getBarco());
		reservaRepository.save(reserva);
		return reservaDto;
	}

	@Override
	public ReservaEntityDTO modificarReserva(String codigoReserva, ReservaEntityDTO reservaDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminarReserva(String codigoReserva) {
		// TODO Auto-generated method stub
		
	}
}
