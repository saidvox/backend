package com.cafedebarrio.backend.controller;

import com.cafedebarrio.backend.dto.pedido.request.ActualizarEstadoPedidoRequest;
import com.cafedebarrio.backend.dto.pedido.request.PedidoRequest;
import com.cafedebarrio.backend.dto.pedido.response.PedidoResponse;
import com.cafedebarrio.backend.exception.ApiErrorResponse;
import com.cafedebarrio.backend.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Gestion de pedidos y flujo de compra")
public class PedidoController {

	private final PedidoService pedidoService;

	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "bearer-jwt")
	@Operation(summary = "Listar pedidos", description = "Lista los pedidos registrados, con filtro opcional por estado.")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Pedidos obtenidos correctamente",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = PedidoResponse.class)))
			),
			@ApiResponse(
					responseCode = "400",
					description = "Estado de pedido invalido",
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
	public List<PedidoResponse> listarPedidos(
			@Parameter(description = "Estado del pedido para filtrar", example = "PENDIENTE")
			@RequestParam(required = false) String estado
	) {
		return pedidoService.listarPedidos(estado);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Crear pedido", description = "Registra un pedido con datos del cliente, valida stock y descuenta existencias.")
	@ApiResponses({
			@ApiResponse(
					responseCode = "201",
					description = "Pedido creado correctamente",
					content = @Content(schema = @Schema(implementation = PedidoResponse.class))
			),
			@ApiResponse(
					responseCode = "400",
					description = "Solicitud invalida o producto no disponible",
					content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
			),
			@ApiResponse(
					responseCode = "404",
					description = "Producto no encontrado",
					content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
			),
			@ApiResponse(
					responseCode = "409",
					description = "Stock insuficiente",
					content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
			)
	})
	public PedidoResponse crearPedido(@Valid @RequestBody PedidoRequest pedidoRequest) {
		return pedidoService.crearPedido(pedidoRequest);
	}

	@PatchMapping("/{id}/estado")
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "bearer-jwt")
	@Operation(summary = "Actualizar estado del pedido", description = "Cambia el estado del pedido a pendiente, en preparacion o entregado.")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Estado actualizado correctamente",
					content = @Content(schema = @Schema(implementation = PedidoResponse.class))
			),
			@ApiResponse(
					responseCode = "400",
					description = "Solicitud invalida",
					content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
			),
			@ApiResponse(
					responseCode = "404",
					description = "Pedido no encontrado",
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
	public PedidoResponse actualizarEstado(
			@PathVariable Long id,
			@Valid @RequestBody ActualizarEstadoPedidoRequest request
	) {
		return pedidoService.actualizarEstado(id, request);
	}
}
