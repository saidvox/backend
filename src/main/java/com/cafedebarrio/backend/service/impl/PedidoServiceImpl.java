package com.cafedebarrio.backend.service.impl;

import com.cafedebarrio.backend.dto.pedido.request.ActualizarEstadoPedidoRequest;
import com.cafedebarrio.backend.dto.pedido.request.PedidoItemRequest;
import com.cafedebarrio.backend.dto.pedido.request.PedidoRequest;
import com.cafedebarrio.backend.dto.pedido.response.PedidoResponse;
import com.cafedebarrio.backend.entity.DetallePedido;
import com.cafedebarrio.backend.entity.Pedido;
import com.cafedebarrio.backend.entity.Producto;
import com.cafedebarrio.backend.enums.EstadoPedido;
import com.cafedebarrio.backend.exception.BusinessException;
import com.cafedebarrio.backend.exception.ResourceNotFoundException;
import com.cafedebarrio.backend.exception.StockInsuficienteException;
import com.cafedebarrio.backend.mapper.PedidoMapper;
import com.cafedebarrio.backend.realtime.PedidoActualizadoEvent;
import com.cafedebarrio.backend.realtime.PedidoCreadoEvent;
import com.cafedebarrio.backend.repository.PedidoRepository;
import com.cafedebarrio.backend.repository.ProductoRepository;
import com.cafedebarrio.backend.service.PedidoService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoServiceImpl implements PedidoService {

	private final PedidoRepository pedidoRepository;
	private final ProductoRepository productoRepository;
	private final PedidoMapper pedidoMapper;
	private final ApplicationEventPublisher eventPublisher;

	public PedidoServiceImpl(
			PedidoRepository pedidoRepository,
			ProductoRepository productoRepository,
			PedidoMapper pedidoMapper,
			ApplicationEventPublisher eventPublisher
	) {
		this.pedidoRepository = pedidoRepository;
		this.productoRepository = productoRepository;
		this.pedidoMapper = pedidoMapper;
		this.eventPublisher = eventPublisher;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PedidoResponse> listarPedidos(String estado) {
		List<Pedido> pedidos;

		if (estado == null || estado.isBlank()) {
			pedidos = pedidoRepository.findAllByOrderByFechaDesc();
		} else {
			try {
				EstadoPedido estadoPedido = EstadoPedido.valueOf(estado.trim().toUpperCase());
				pedidos = pedidoRepository.findByEstadoOrderByFechaDesc(estadoPedido);
			} catch (IllegalArgumentException ex) {
				throw new BusinessException("Estado de pedido no valido: " + estado);
			}
		}

		return pedidos.stream()
				.map(pedidoMapper::toResponse)
				.toList();
	}

	@Override
	@Transactional
	public PedidoResponse crearPedido(PedidoRequest pedidoRequest) {
		Pedido pedido = pedidoMapper.toEntity(pedidoRequest);
		BigDecimal total = BigDecimal.ZERO;
		List<Long> productosActualizados = new ArrayList<>();

		for (PedidoItemRequest item : pedidoRequest.items()) {
			Producto producto = productoRepository.findById(item.productoId())
					.orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + item.productoId()));

			if (Boolean.FALSE.equals(producto.getActivo())) {
				throw new BusinessException("El producto no esta disponible: " + producto.getNombre());
			}

			if (producto.getStock() < item.cantidad()) {
				throw new StockInsuficienteException("Stock insuficiente para el producto: " + producto.getNombre());
			}

			BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(item.cantidad()));

			DetallePedido detallePedido = DetallePedido.builder()
					.producto(producto)
					.cantidad(item.cantidad())
					.precioUnitario(producto.getPrecio())
					.subtotal(subtotal)
					.build();

			pedido.addDetalle(detallePedido);
			total = total.add(subtotal);
			producto.setStock(producto.getStock() - item.cantidad());
			productosActualizados.add(producto.getId());
		}

		pedido.setTotal(total);
		Pedido pedidoGuardado = pedidoRepository.save(pedido);
		eventPublisher.publishEvent(new PedidoCreadoEvent(pedidoGuardado.getId(), productosActualizados));
		return pedidoMapper.toResponse(pedidoGuardado);
	}

	@Override
	@Transactional
	public PedidoResponse actualizarEstado(Long id, ActualizarEstadoPedidoRequest request) {
		Pedido pedido = pedidoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + id));

		pedido.setEstado(request.estado());
		eventPublisher.publishEvent(new PedidoActualizadoEvent(id));
		return pedidoMapper.toResponse(pedido);
	}
}
