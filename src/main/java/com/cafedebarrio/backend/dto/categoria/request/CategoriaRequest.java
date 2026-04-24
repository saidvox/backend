package com.cafedebarrio.backend.dto.categoria.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequest(
		@Schema(description = "Nombre de la categoria", example = "Cafe")
		@NotBlank(message = "El nombre de la categoria es obligatorio")
		@Size(max = 100, message = "El nombre de la categoria no puede superar los 100 caracteres")
		String nombre
) {
}
