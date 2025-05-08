package com.riverBooking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.riverBooking.security.JwtAuthFilter;
import com.riverBooking.security.JwtUtil;

@Configuration
public class SecurityConfig {

	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetails;

	public SecurityConfig(JwtUtil jwtUtil, UserDetailsService userDetails) {
		this.jwtUtil = jwtUtil;
		this.userDetails = userDetails;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/login").permitAll()
						.requestMatchers("/reservas/guardar")
						.permitAll()
						.anyRequest().authenticated())
				.addFilterBefore(new JwtAuthFilter(jwtUtil, userDetails), UsernamePasswordAuthenticationFilter.class);

		return http.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
