package com.cafedebarrio.backend.dto.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Respuesta de autenticacion JWT")
public record AuthResponse(
		@Schema(description = "Token JWT de acceso", example = "eyJhbGciOiJIUzI1NiJ9...")
		String accessToken,

		@Schema(description = "Tipo de token", example = "Bearer")
		String tokenType,

		@Schema(description = "Tiempo de expiracion en milisegundos", example = "900000")
		long expiresIn,

		@Schema(description = "Usuario autenticado", example = "admin")
		String username,

		@Schema(description = "Roles del usuario autenticado", example = "[\"ROLE_ADMIN\"]")
		List<String> roles
) {
}
