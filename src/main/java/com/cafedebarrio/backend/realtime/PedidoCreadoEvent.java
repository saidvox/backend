package com.cafedebarrio.backend.realtime;

import java.util.List;

public record PedidoCreadoEvent(Long pedidoId, List<Long> productoIds) {

	public PedidoCreadoEvent {
		productoIds = List.copyOf(productoIds);
	}
}
