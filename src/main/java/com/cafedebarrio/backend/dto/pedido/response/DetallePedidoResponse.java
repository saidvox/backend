package com.cafedebarrio.backend.dto.pedido.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Detalle de un producto incluido en el pedido")
public record DetallePedidoResponse(
		@Schema(description = "Identificador del detalle", example = "1")
		Long id,
		@Schema(description = "Id del producto", example = "1")
		Long productoId,
		@Schema(description = "Nombre del producto", example = "Cafe de Altura 250g")
		String productoNombre,
		@Schema(description = "Cantidad solicitada", example = "2")
		Integer cantidad,
		@Schema(description = "Precio unitario del producto", example = "25.90")
		BigDecimal precioUnitario,
		@Schema(description = "Subtotal del detalle", example = "51.80")
		BigDecimal subtotal
) {
}
