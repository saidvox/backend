package com.cafedebarrio.backend.realtime;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/realtime")
public class RealtimeController {

	private final RealtimeNotificationService realtimeNotificationService;

	public RealtimeController(RealtimeNotificationService realtimeNotificationService) {
		this.realtimeNotificationService = realtimeNotificationService;
	}

	@GetMapping(path = "/catalogo", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter catalogo() {
		return realtimeNotificationService.subscribeCatalog();
	}

	@GetMapping(path = "/admin/pedidos", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public SseEmitter pedidosAdmin() {
		return realtimeNotificationService.subscribeOrders();
	}
}
