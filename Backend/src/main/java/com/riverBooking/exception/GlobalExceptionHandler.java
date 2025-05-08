package com.riverBooking.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiError> handleRunTimeException(RuntimeException ex) {
		return new ResponseEntity<>(buildError(HttpStatus.INTERNAL_SERVER_ERROR, ex), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ReservaNoEncontradaException.class)
	public ResponseEntity<ApiError> handleReservaException(ReservaNoEncontradaException ex) {
		return new ResponseEntity<>(buildError(HttpStatus.NOT_FOUND, ex), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PlazasInsuficientesException.class)
	public ResponseEntity<ApiError> handlePlazasException(PlazasInsuficientesException ex) {
		return new ResponseEntity<>(buildError(HttpStatus.CONFLICT, ex), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(BarcoNoEncontradoException.class)
	public ResponseEntity<ApiError> handleBarcoException(BarcoNoEncontradoException ex) {

		return new ResponseEntity<>(buildError(HttpStatus.NOT_FOUND, ex), HttpStatus.NOT_FOUND);
	}

	public ApiError buildError(HttpStatus status, Exception mensaje) {
		return new ApiError(status, mensaje.getMessage(), LocalDateTime.now());
	}
}
