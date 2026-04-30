package com.cafedebarrio.backend.realtime;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class RealtimeNotificationService {

	private static final long STREAM_TIMEOUT_MS = 30 * 60 * 1000L;

	private final Set<SseEmitter> catalogEmitters = ConcurrentHashMap.newKeySet();
	private final Set<SseEmitter> orderEmitters = ConcurrentHashMap.newKeySet();

	public SseEmitter subscribeCatalog() {
		return subscribe(catalogEmitters);
	}

	public SseEmitter subscribeOrders() {
		return subscribe(orderEmitters);
	}

	public void publishCatalogChanged(List<Long> productoIds) {
		broadcast(catalogEmitters, "catalog-changed", RealtimeMessage.catalogChanged(productoIds));
	}

	public void publishOrdersChanged(Long pedidoId) {
		broadcast(orderEmitters, "orders-changed", RealtimeMessage.ordersChanged(pedidoId));
	}

	private SseEmitter subscribe(Set<SseEmitter> emitters) {
		SseEmitter emitter = new SseEmitter(STREAM_TIMEOUT_MS);
		emitters.add(emitter);

		emitter.onCompletion(() -> emitters.remove(emitter));
		emitter.onTimeout(() -> {
			emitters.remove(emitter);
			emitter.complete();
		});
		emitter.onError(error -> emitters.remove(emitter));

		send(emitter, "connected", RealtimeMessage.connected());
		return emitter;
	}

	private void broadcast(Set<SseEmitter> emitters, String eventName, RealtimeMessage message) {
		for (SseEmitter emitter : emitters) {
			if (!send(emitter, eventName, message)) {
				emitters.remove(emitter);
			}
		}
	}

	private boolean send(SseEmitter emitter, String eventName, RealtimeMessage message) {
		try {
			emitter.send(SseEmitter.event()
					.name(eventName)
					.data(message));
			return true;
		} catch (IOException | IllegalStateException ex) {
			emitter.completeWithError(ex);
			return false;
		}
	}
}
