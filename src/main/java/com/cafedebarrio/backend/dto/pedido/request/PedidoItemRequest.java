package com.cafedebarrio.backend.dto.pedido.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Producto y cantidad solicitada dentro de un pedido")
public record PedidoItemRequest(
		@Schema(description = "Id del producto", example = "1")
		@NotNull(message = "El id del producto es obligatorio")
		Long productoId,

		@Schema(description = "Cantidad solicitada", example = "2")
		@NotNull(message = "La cantidad es obligatoria")
		@Positive(message = "La cantidad debe ser mayor a 0")
		Integer cantidad
) {
}
