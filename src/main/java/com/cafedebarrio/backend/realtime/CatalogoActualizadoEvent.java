package com.cafedebarrio.backend.realtime;

import java.util.List;

public record CatalogoActualizadoEvent(List<Long> productoIds) {

	public CatalogoActualizadoEvent {
		productoIds = List.copyOf(productoIds);
	}
}
