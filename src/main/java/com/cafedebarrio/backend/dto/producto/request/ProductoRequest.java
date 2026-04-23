package com.cafedebarrio.backend.dto.producto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProductoRequest(
		@NotBlank(message = "El nombre del producto es obligatorio")
		@Size(max = 150, message = "El nombre del producto no puede superar los 150 caracteres")
		String nombre,

		@NotBlank(message = "La descripcion del producto es obligatoria")
		@Size(max = 1000, message = "La descripcion del producto no puede superar los 1000 caracteres")
		String descripcion,

		@NotNull(message = "El precio del producto es obligatorio")
		@DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
		BigDecimal precio,

		@NotNull(message = "El stock del producto es obligatorio")
		@PositiveOrZero(message = "El stock no puede ser negativo")
		Integer stock,

		@Size(max = 500, message = "La imagen del producto no puede superar los 500 caracteres")
		String imagenUrl,

		@NotNull(message = "El estado activo del producto es obligatorio")
		Boolean activo,

		@NotNull(message = "La categoria del producto es obligatoria")
		Long categoriaId
) {
}
