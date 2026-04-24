package com.cafedebarrio.backend.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cafedebarrio.backend.dto.pedido.request.ActualizarEstadoPedidoRequest;
import com.cafedebarrio.backend.dto.pedido.request.PedidoItemRequest;
import com.cafedebarrio.backend.dto.pedido.request.PedidoRequest;
import com.cafedebarrio.backend.dto.pedido.response.PedidoResponse;
import com.cafedebarrio.backend.entity.Pedido;
import com.cafedebarrio.backend.entity.Producto;
import com.cafedebarrio.backend.enums.EstadoPedido;
import com.cafedebarrio.backend.exception.BusinessException;
import com.cafedebarrio.backend.exception.ResourceNotFoundException;
import com.cafedebarrio.backend.exception.StockInsuficienteException;
import com.cafedebarrio.backend.mapper.PedidoMapper;
import com.cafedebarrio.backend.repository.PedidoRepository;
import com.cafedebarrio.backend.repository.ProductoRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {

	@Mock
	private PedidoRepository pedidoRepository;

	@Mock
	private ProductoRepository productoRepository;

	@Mock
	private PedidoMapper pedidoMapper;

	@InjectMocks
	private PedidoServiceImpl pedidoService;

	@Test
	@DisplayName("Should create order and discount stock when all products are available")
	void shouldCreateOrderAndDiscountStockWhenAllProductsAreAvailable() {
		PedidoRequest request = new PedidoRequest(
				"Juan Perez",
				"987654321",
				"Av. Los Cafetos 123",
				List.of(
						new PedidoItemRequest(1L, 2),
						new PedidoItemRequest(4L, 1)
				)
		);

		Producto cafeAltura = Producto.builder()
				.id(1L)
				.nombre("Cafe de Altura 250g")
				.precio(new BigDecimal("25.90"))
				.stock(20)
				.activo(true)
				.build();

		Producto cafeGeisha = Producto.builder()
				.id(4L)
				.nombre("Cafe Geisha 340g")
				.precio(new BigDecimal("45.50"))
				.stock(6)
				.activo(true)
				.build();

		Pedido pedidoNuevo = Pedido.builder().build();

		when(pedidoMapper.toEntity(request)).thenReturn(pedidoNuevo);
		when(productoRepository.findById(1L)).thenReturn(Optional.of(cafeAltura));
		when(productoRepository.findById(4L)).thenReturn(Optional.of(cafeGeisha));
		when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> {
			Pedido pedido = invocation.getArgument(0);
			if (pedido.getEstado() == null) {
				pedido.setEstado(EstadoPedido.PENDIENTE);
			}
			return pedido;
		});
		when(pedidoMapper.toResponse(any(Pedido.class))).thenAnswer(invocation -> {
			Pedido pedido = invocation.getArgument(0);
			return new PedidoResponse(
					1L,
					request.clienteNombre(),
					request.celular(),
					request.direccion(),
					LocalDateTime.now(),
					pedido.getEstado(),
					pedido.getTotal(),
					List.of()
			);
		});

		PedidoResponse response = pedidoService.crearPedido(request);

		assertThat(response.total()).isEqualByComparingTo("97.30");
		assertThat(response.estado()).isEqualTo(EstadoPedido.PENDIENTE);
		assertThat(cafeAltura.getStock()).isEqualTo(18);
		assertThat(cafeGeisha.getStock()).isEqualTo(5);
		verify(pedidoRepository).save(pedidoNuevo);
	}

	@Test
	@DisplayName("Should reject order when product stock is insufficient")
	void shouldRejectOrderWhenProductStockIsInsufficient() {
		PedidoRequest request = new PedidoRequest(
				"Juan Perez",
				"987654321",
				"Av. Los Cafetos 123",
				List.of(new PedidoItemRequest(1L, 3))
		);

		Producto producto = Producto.builder()
				.id(1L)
				.nombre("Cafe de Altura 250g")
				.precio(new BigDecimal("25.90"))
				.stock(2)
				.activo(true)
				.build();

		when(pedidoMapper.toEntity(request)).thenReturn(Pedido.builder().build());
		when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

		assertThatThrownBy(() -> pedidoService.crearPedido(request))
				.isInstanceOf(StockInsuficienteException.class)
				.hasMessageContaining("Stock insuficiente");

		verify(pedidoRepository, never()).save(any(Pedido.class));
	}

	@Test
	@DisplayName("Should reject order listing when state filter is invalid")
	void shouldRejectOrderListingWhenStateFilterIsInvalid() {
		assertThatThrownBy(() -> pedidoService.listarPedidos("inventado"))
				.isInstanceOf(BusinessException.class)
				.hasMessageContaining("Estado de pedido no valido");
	}

	@Test
	@DisplayName("Should fail when updating status of a non existing order")
	void shouldFailWhenUpdatingStatusOfNonExistingOrder() {
		when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> pedidoService.actualizarEstado(99L, new ActualizarEstadoPedidoRequest(EstadoPedido.ENTREGADO)))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessageContaining("Pedido no encontrado");
	}
}
