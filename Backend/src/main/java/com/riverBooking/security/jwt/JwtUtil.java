package com.riverBooking.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration-ms}")
	private Long expMs;
	
	private SecretKey secretKey;
	
	@PostConstruct
	private void initKey() {
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
	}
	
	public String generarToken(String usuario) {
		Date creacion = new Date();
		Date exp = new Date(creacion.getTime() + expMs);
		
		return Jwts.builder()
				.setSubject(usuario)
				.setIssuedAt(creacion)
				.setExpiration(exp)
				.signWith(secretKey, SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String getUsuario(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public boolean tokenValido(String token) {
		try {
			getUsuario(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}
