package com.cafedebarrio.backend.dto.pedido.response;

import com.cafedebarrio.backend.enums.EstadoPedido;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponse(
		Long id,
		String clienteNombre,
		String celular,
		String direccion,
		LocalDateTime fecha,
		EstadoPedido estado,
		BigDecimal total,
		List<DetallePedidoResponse> detalles
) {
}
