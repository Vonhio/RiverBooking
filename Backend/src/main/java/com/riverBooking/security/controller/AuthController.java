package com.riverBooking.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riverBooking.security.dto.LoginRequestDTO;
import com.riverBooking.security.dto.LoginResponseDTO;
import com.riverBooking.security.jwt.JwtUtil;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthenticationManager authManager;
	private final JwtUtil jwtUtil;
	
	public AuthController (AuthenticationManager authManager, JwtUtil jwtUtil) {
		this.authManager = authManager;
		this.jwtUtil = jwtUtil;
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
	    Authentication auth = new UsernamePasswordAuthenticationToken(
	        loginRequest.getUsername(),
	        loginRequest.getPassword()
	    );

	    authManager.authenticate(auth);

	    String token = jwtUtil.generarToken(loginRequest.getUsername());

	    return ResponseEntity.ok(new LoginResponseDTO(token, loginRequest.getUsername()));
	}

}
