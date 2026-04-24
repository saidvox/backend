package com.cafedebarrio.backend.dto.producto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Detalle completo de un producto")
public record ProductoDetalleResponse(
		@Schema(description = "Identificador del producto", example = "1")
		Long id,
		@Schema(description = "Nombre del producto", example = "Cafe de Altura 250g")
		String nombre,
		@Schema(description = "Descripcion del producto", example = "Cafe en grano de tostado medio con notas a chocolate.")
		String descripcion,
		@Schema(description = "Precio del producto", example = "25.90")
		BigDecimal precio,
		@Schema(description = "Stock disponible", example = "20")
		Integer stock,
		@Schema(description = "URL de imagen referencial", example = "https://images.unsplash.com/photo-1447933601403-0c6688de566e")
		String imagenUrl,
		@Schema(description = "Estado del producto", example = "true")
		Boolean activo,
		@Schema(description = "Id de la categoria", example = "1")
		Long categoriaId,
		@Schema(description = "Nombre de la categoria", example = "Cafe")
		String categoriaNombre
) {
}
