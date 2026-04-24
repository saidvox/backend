package com.cafedebarrio.backend.dto.pedido.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

@Schema(description = "Datos necesarios para registrar un pedido")
public record PedidoRequest(
		@Schema(description = "Nombre del cliente", example = "Juan Perez")
		@NotBlank(message = "El nombre del cliente es obligatorio")
		@Size(max = 150, message = "El nombre del cliente no puede superar los 150 caracteres")
		String clienteNombre,

		@Schema(description = "Celular de contacto", example = "987654321")
		@NotBlank(message = "El celular es obligatorio")
		@Size(max = 20, message = "El celular no puede superar los 20 caracteres")
		String celular,

		@Schema(description = "Direccion de entrega", example = "Av. Los Cafetos 123, Miraflores")
		@NotBlank(message = "La direccion es obligatoria")
		@Size(max = 255, message = "La direccion no puede superar los 255 caracteres")
		String direccion,

		@Schema(description = "Lista de productos solicitados")
		@NotEmpty(message = "El pedido debe tener al menos un producto")
		List<@Valid PedidoItemRequest> items
) {
}
