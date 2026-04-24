package com.cafedebarrio.backend.controller;

import com.cafedebarrio.backend.dto.auth.request.LoginRequest;
import com.cafedebarrio.backend.dto.auth.response.AuthResponse;
import com.cafedebarrio.backend.exception.ApiErrorResponse;
import com.cafedebarrio.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticacion", description = "Acceso administrativo mediante JWT")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Iniciar sesion", description = "Autentica al administrador y devuelve un token JWT Bearer.")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Autenticacion correcta",
					content = @Content(schema = @Schema(implementation = AuthResponse.class))
			),
			@ApiResponse(
					responseCode = "400",
					description = "Solicitud invalida",
					content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
			),
			@ApiResponse(
					responseCode = "401",
					description = "Credenciales invalidas",
					content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
			)
	})
	public AuthResponse login(@Valid @RequestBody LoginRequest request) {
		return authService.login(request);
	}
}
