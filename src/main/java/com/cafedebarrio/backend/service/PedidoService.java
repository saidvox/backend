package com.cafedebarrio.backend.service;

import com.cafedebarrio.backend.dto.pedido.request.ActualizarEstadoPedidoRequest;
import com.cafedebarrio.backend.dto.pedido.request.PedidoRequest;
import com.cafedebarrio.backend.dto.pedido.response.PedidoResponse;
import java.util.List;

public interface PedidoService {

	List<PedidoResponse> listarPedidos(String estado);

	PedidoResponse crearPedido(PedidoRequest pedidoRequest);

	PedidoResponse actualizarEstado(Long id, ActualizarEstadoPedidoRequest request);
}
