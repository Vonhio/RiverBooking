package com.riverBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riverBooking.entity.ReservaEntity;

public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {
	boolean existsByCodigoReserva(String codigoReserva);
}
