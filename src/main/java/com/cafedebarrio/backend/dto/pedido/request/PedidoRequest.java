package com.cafedebarrio.backend.dto.pedido.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

public record PedidoRequest(
		@NotBlank(message = "El nombre del cliente es obligatorio")
		@Size(max = 150, message = "El nombre del cliente no puede superar los 150 caracteres")
		String clienteNombre,

		@NotBlank(message = "El celular es obligatorio")
		@Size(max = 20, message = "El celular no puede superar los 20 caracteres")
		String celular,

		@NotBlank(message = "La direccion es obligatoria")
		@Size(max = 255, message = "La direccion no puede superar los 255 caracteres")
		String direccion,

		@NotEmpty(message = "El pedido debe tener al menos un producto")
		List<@Valid PedidoItemRequest> items
) {
}
