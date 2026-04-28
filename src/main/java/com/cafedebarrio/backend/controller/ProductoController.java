package com.cafedebarrio.backend.controller;

import com.cafedebarrio.backend.dto.producto.request.ProductoRequest;
import com.cafedebarrio.backend.dto.producto.response.ProductoDetalleResponse;
import com.cafedebarrio.backend.dto.producto.response.ProductoResponse;
import com.cafedebarrio.backend.exception.ApiErrorResponse;
import com.cafedebarrio.backend.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Gestion del catalogo de productos")
public class ProductoController {

	private final ProductoService productoService;

	public ProductoController(ProductoService productoService) {
		this.productoService = productoService;
	}

	@GetMapping
	@Operation(summary = "Listar productos", description = "Lista productos del catalogo con filtros opcionales por categoria, estado activo y disponibilidad de stock.")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Productos obtenidos correctamente",
					content = @Content(schema = @Schema(implementation = org.springframework.data.domain.Page.class))
			),
			@ApiResponse(
					responseCode = "500",
					description = "Error interno del servidor",
					content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
			)
	})
	public org.springframework.data.domain.Page<ProductoResponse> listarProductos(
			@Parameter(description = "Id de la categoria para filtrar productos", example = "1")
			@RequestParam(required = false) Long categoria,
			@Parameter(description = "Texto para buscar productos por nombre", example = "cafe")
			@RequestParam(required = false) String nombre,
			@Parameter(description = "Indica si solo se deben listar productos activos", example = "true")
			@RequestParam(required = false) Boolean activos,
			@Parameter(description = "Indica si solo se deben listar productos con stock mayor a cero", example = "true")
			@RequestParam(required = false) Boolean disponible,
			@Parameter(description = "Numero de pagina (comienza en 0)", example = "0")
			@RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Tamaño de pagina", example = "10")
			@RequestParam(defaultValue = "10") int size
	) {
		org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
		return productoService.listarProductos(categoria, nombre, activos, disponible, pageable);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener detalle de producto", description = "Devuelve la informacion detallada de un producto por su identificador.")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Producto encontrado",
					content = @Content(schema = @Schema(implementation = ProductoDetalleResponse.class))
			),
			@ApiResponse(
					responseCode = "404",
					description = "Producto no encontrado",
					content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
			)
	})
	public ProductoDetalleResponse obtenerProductoPorId(@PathVariable Long id) {
		return productoService.obtenerProductoPorId(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "bearer-jwt")
	@Operation(summary = "Crear producto", description = "Registra un nuevo producto para el catalogo administrativo.")
	@ApiResponses({
			@ApiResponse(
					responseCode = "201",
					description = "Producto creado correctamente",
					content = @Content(schema = @Schema(implementation = ProductoResponse.class))
			),
			@ApiResponse(
					responseCode = "400",
					description = "Solicitud invalida",
					content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
			),
			@ApiResponse(
					responseCode = "404",
					description = "Categoria no encontrada",
					content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
			),
			@ApiResponse(
					responseCode = "401",
					description = "No autenticado",
					content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
			),
			@ApiResponse(
					responseCode = "403",
					description = "No autorizado",
					content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
			)
	})
	public ProductoResponse crearProducto(@Valid @RequestBody ProductoRequest productoRequest) {
		return productoService.crearProducto(productoRequest);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "bearer-jwt")
	@Operation(summary = "Actualizar producto", description = "Actualiza nombre, descripcion, precio, stock, imagen, estado y categoria de un producto.")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Producto actualizado correctamente",
					content = @Content(schema = @Schema(implementation = ProductoResponse.class))
			),
			@ApiResponse(
					responseCode = "400",
					description = "Solicitud invalida",
					content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
			),
			@ApiResponse(
					responseCode = "404",
					description = "Producto o categoria no encontrados",
					content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
			),
			@ApiResponse(
					responseCode = "401",
					description = "No autenticado",
					content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
			),
			@ApiResponse(
					responseCode = "403",
					description = "No autorizado",
					content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
			)
	})
	public ProductoResponse actualizarProducto(
			@PathVariable Long id,
			@Valid @RequestBody ProductoRequest productoRequest
	) {
		return productoService.actualizarProducto(id, productoRequest);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "bearer-jwt")
	@Operation(summary = "Desactivar producto", description = "Realiza una eliminacion logica del producto marcandolo como inactivo.")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Producto desactivado correctamente"),
			@ApiResponse(
					responseCode = "404",
					description = "Producto no encontrado",
					content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
			),
			@ApiResponse(
					responseCode = "401",
					description = "No autenticado",
					content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
			),
			@ApiResponse(
					responseCode = "403",
					description = "No autorizado",
					content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
			)
	})
	public void desactivarProducto(@PathVariable Long id) {
		productoService.desactivarProducto(id);
	}
}
