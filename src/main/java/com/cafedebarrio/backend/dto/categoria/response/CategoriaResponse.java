package com.cafedebarrio.backend.dto.categoria.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Categoria del catalogo")
public record CategoriaResponse(
		@Schema(description = "Identificador de la categoria", example = "1")
		Long id,
		@Schema(description = "Nombre de la categoria", example = "Cafe")
		String nombre
) {
}
