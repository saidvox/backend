package com.cafedebarrio.backend.dto.pedido.response;

import java.math.BigDecimal;

public record DetallePedidoResponse(
		Long id,
		Long productoId,
		String productoNombre,
		Integer cantidad,
		BigDecimal precioUnitario,
		BigDecimal subtotal
) {
}
