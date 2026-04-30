package com.cafedebarrio.backend.realtime;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class RealtimeEventListener {

	private final RealtimeNotificationService realtimeNotificationService;

	public RealtimeEventListener(RealtimeNotificationService realtimeNotificationService) {
		this.realtimeNotificationService = realtimeNotificationService;
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void onCatalogoActualizado(CatalogoActualizadoEvent event) {
		realtimeNotificationService.publishCatalogChanged(event.productoIds());
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void onPedidoCreado(PedidoCreadoEvent event) {
		realtimeNotificationService.publishOrdersChanged(event.pedidoId());
		realtimeNotificationService.publishCatalogChanged(event.productoIds());
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void onPedidoActualizado(PedidoActualizadoEvent event) {
		realtimeNotificationService.publishOrdersChanged(event.pedidoId());
	}
}
