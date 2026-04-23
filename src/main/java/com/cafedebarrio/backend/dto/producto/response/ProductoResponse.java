package com.cafedebarrio.backend.dto.producto.response;

import java.math.BigDecimal;

public record ProductoResponse(
		Long id,
		String nombre,
		String descripcion,
		BigDecimal precio,
		Integer stock,
		String imagenUrl,
		Boolean activo,
		Long categoriaId,
		String categoriaNombre
) {
}
