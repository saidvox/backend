package com.cafedebarrio.backend.dto.pedido.response;

import com.cafedebarrio.backend.enums.EstadoPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Respuesta de un pedido registrado")
public record PedidoResponse(
		@Schema(description = "Identificador del pedido", example = "1")
		Long id,
		@Schema(description = "Nombre del cliente", example = "Juan Perez")
		String clienteNombre,
		@Schema(description = "Celular del cliente", example = "987654321")
		String celular,
		@Schema(description = "Direccion de entrega", example = "Av. Los Cafetos 123, Miraflores")
		String direccion,
		@Schema(description = "Fecha de registro del pedido", example = "2026-04-24T14:30:00")
		LocalDateTime fecha,
		@Schema(description = "Estado actual del pedido", example = "PENDIENTE")
		EstadoPedido estado,
		@Schema(description = "Total calculado del pedido", example = "51.80")
		BigDecimal total,
		@Schema(description = "Detalle de productos incluidos en el pedido")
		List<DetallePedidoResponse> detalles
) {
}
