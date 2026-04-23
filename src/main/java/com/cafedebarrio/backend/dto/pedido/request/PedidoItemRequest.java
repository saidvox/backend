package com.cafedebarrio.backend.dto.pedido.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PedidoItemRequest(
		@NotNull(message = "El id del producto es obligatorio")
		Long productoId,

		@NotNull(message = "La cantidad es obligatoria")
		@Positive(message = "La cantidad debe ser mayor a 0")
		Integer cantidad
) {
}
