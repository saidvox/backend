package com.cafedebarrio.backend.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Respuesta estandar de error de la API")
public record ApiErrorResponse(
		@Schema(description = "Fecha y hora del error", example = "2026-04-24T14:35:00")
		LocalDateTime timestamp,
		@Schema(description = "Codigo HTTP", example = "400")
		int status,
		@Schema(description = "Descripcion corta del error HTTP", example = "Bad Request")
		String error,
		@Schema(description = "Mensaje principal del error", example = "Error de validacion en la solicitud")
		String message,
		@Schema(description = "Lista de detalles del error")
		List<String> details
) {
}
