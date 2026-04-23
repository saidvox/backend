package com.cafedebarrio.backend.dto.pedido.request;

import com.cafedebarrio.backend.enums.EstadoPedido;
import jakarta.validation.constraints.NotNull;

public record ActualizarEstadoPedidoRequest(
		@NotNull(message = "El estado del pedido es obligatorio")
		EstadoPedido estado
) {
}
