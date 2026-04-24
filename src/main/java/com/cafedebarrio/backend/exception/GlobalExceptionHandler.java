package com.cafedebarrio.backend.exception;

import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), List.of());
	}

	@ExceptionHandler(StockInsuficienteException.class)
	public ResponseEntity<ApiErrorResponse> handleStockInsuficiente(StockInsuficienteException ex) {
		return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), List.of());
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiErrorResponse> handleBusiness(BusinessException ex) {
		return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), List.of());
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiErrorResponse> handleBadCredentials(BadCredentialsException ex) {
		return buildResponse(HttpStatus.UNAUTHORIZED, "Credenciales invalidas", List.of());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiErrorResponse> handleAccessDenied(AccessDeniedException ex) {
		return buildResponse(HttpStatus.FORBIDDEN, "No tienes permisos para realizar esta accion", List.of());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		List<String> details = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.toList();

		return buildResponse(HttpStatus.BAD_REQUEST, "Error de validacion en la solicitud", details);
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<ApiErrorResponse> handleBindException(BindException ex) {
		List<String> details = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.toList();

		return buildResponse(HttpStatus.BAD_REQUEST, "Error al procesar los parametros de la solicitud", details);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
		List<String> details = ex.getConstraintViolations()
				.stream()
				.map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
				.toList();

		return buildResponse(HttpStatus.BAD_REQUEST, "Error de validacion", details);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex) {
		return buildResponse(
				HttpStatus.INTERNAL_SERVER_ERROR,
				"Ocurrio un error interno en el servidor",
				List.of()
		);
	}

	private ResponseEntity<ApiErrorResponse> buildResponse(HttpStatus status, String message, List<String> details) {
		ApiErrorResponse response = new ApiErrorResponse(
				LocalDateTime.now(),
				status.value(),
				status.getReasonPhrase(),
				message,
				details
		);

		return ResponseEntity.status(status).body(response);
	}
}
