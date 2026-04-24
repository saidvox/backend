package com.cafedebarrio.backend.controller;

import com.cafedebarrio.backend.dto.categoria.response.CategoriaResponse;
import com.cafedebarrio.backend.exception.ApiErrorResponse;
import com.cafedebarrio.backend.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorias", description = "Consulta de categorias del catalogo")
public class CategoriaController {

	private final CategoriaService categoriaService;

	public CategoriaController(CategoriaService categoriaService) {
		this.categoriaService = categoriaService;
	}

	@GetMapping
	@Operation(summary = "Listar categorias", description = "Obtiene todas las categorias disponibles del catalogo.")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Categorias obtenidas correctamente",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoriaResponse.class)))
			),
			@ApiResponse(
					responseCode = "500",
					description = "Error interno del servidor",
					content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
			)
	})
	public List<CategoriaResponse> listarCategorias() {
		return categoriaService.listarCategorias();
	}
}
