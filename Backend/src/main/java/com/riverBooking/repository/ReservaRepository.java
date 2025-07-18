package com.riverBooking.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.riverBooking.entity.ReservaEntity;


public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {
	boolean existsByCodigoReserva(String codigoReserva);
	
	@Query("SELECT COALESCE(SUM(r.numPersonas), 0) FROM ReservaEntity r WHERE r.fechaReserva = :fechaHora AND r.barco.id = :barcoId")
	int numeroPlazasReservadas(LocalDateTime fechaHora, Long barcoId);
	
	@Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM ReservaEntity r WHERE r.fechaReserva = :fechaHora AND r.barco.id = :barcoId AND r.tipoReserva = 'Privado'")
	boolean existeReservaPrivada(LocalDateTime fechaHora, Long barcoId);
}
