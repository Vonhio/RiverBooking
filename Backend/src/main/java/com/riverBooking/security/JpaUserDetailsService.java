package com.riverBooking.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.riverBooking.entity.UsuarioEntity;
import com.riverBooking.repository.UsuarioRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;

	public JpaUserDetailsService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsuarioEntity usuario = usuarioRepository.findByUsuario(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
		return User.builder()
				.username(usuario.getUsuario())
				.password(usuario.getPassword())
				.roles(usuario.getRol())
				.build();
	}
}