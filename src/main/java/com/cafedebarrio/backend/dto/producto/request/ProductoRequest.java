package com.cafedebarrio.backend.dto.producto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Schema(description = "Datos necesarios para crear o actualizar un producto")
public record ProductoRequest(
		@Schema(description = "Nombre del producto", example = "Cafe de Altura 250g")
		@NotBlank(message = "El nombre del producto es obligatorio")
		@Size(max = 150, message = "El nombre del producto no puede superar los 150 caracteres")
		String nombre,

		@Schema(description = "Descripcion comercial del producto", example = "Cafe en grano de tostado medio con notas a chocolate.")
		@NotBlank(message = "La descripcion del producto es obligatoria")
		@Size(max = 1000, message = "La descripcion del producto no puede superar los 1000 caracteres")
		String descripcion,

		@Schema(description = "Precio del producto", example = "25.90")
		@NotNull(message = "El precio del producto es obligatorio")
		@DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
		BigDecimal precio,

		@Schema(description = "Stock disponible", example = "20")
		@NotNull(message = "El stock del producto es obligatorio")
		@PositiveOrZero(message = "El stock no puede ser negativo")
		Integer stock,

		@Schema(description = "URL de la imagen referencial del producto", example = "https://images.unsplash.com/photo-1447933601403-0c6688de566e")
		@Size(max = 500, message = "La imagen del producto no puede superar los 500 caracteres")
		String imagenUrl,

		@Schema(description = "Indica si el producto esta activo en el catalogo", example = "true")
		@NotNull(message = "El estado activo del producto es obligatorio")
		Boolean activo,

		@Schema(description = "Id de la categoria asociada", example = "1")
		@NotNull(message = "La categoria del producto es obligatoria")
		Long categoriaId
) {
}
