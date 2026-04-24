package com.cafedebarrio.backend.dto.pedido.request;

import com.cafedebarrio.backend.enums.EstadoPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Solicitud para actualizar el estado de un pedido")
public record ActualizarEstadoPedidoRequest(
		@Schema(description = "Nuevo estado del pedido", example = "EN_PREPARACION")
		@NotNull(message = "El estado del pedido es obligatorio")
		EstadoPedido estado
) {
}
