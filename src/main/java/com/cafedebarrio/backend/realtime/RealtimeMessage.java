package com.cafedebarrio.backend.realtime;

import java.time.Instant;
import java.util.List;

public record RealtimeMessage(
		String type,
		Long pedidoId,
		List<Long> productoIds,
		Instant occurredAt
) {
	public static RealtimeMessage connected() {
		return new RealtimeMessage("connected", null, List.of(), Instant.now());
	}

	public static RealtimeMessage catalogChanged(List<Long> productoIds) {
		return new RealtimeMessage("catalog-changed", null, List.copyOf(productoIds), Instant.now());
	}

	public static RealtimeMessage ordersChanged(Long pedidoId) {
		return new RealtimeMessage("orders-changed", pedidoId, List.of(), Instant.now());
	}
}
