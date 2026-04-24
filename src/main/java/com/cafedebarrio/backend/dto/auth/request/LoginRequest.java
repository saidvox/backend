package com.cafedebarrio.backend.dto.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Credenciales para iniciar sesion administrativa")
public record LoginRequest(
		@Schema(description = "Usuario administrador", example = "admin")
		@NotBlank(message = "El usuario es obligatorio")
		String username,

		@Schema(description = "Contrasena del administrador", example = "Admin12345*")
		@NotBlank(message = "La contrasena es obligatoria")
		String password
) {
}
