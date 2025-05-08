package com.riverBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riverBooking.entity.UsuarioEntity;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
	Optional<UsuarioEntity> findByUsuario(String usuario);
}
