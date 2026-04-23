package com.cafedebarrio.backend.repository;

import com.cafedebarrio.backend.entity.Pedido;
import com.cafedebarrio.backend.enums.EstadoPedido;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	@Override
	@EntityGraph(attributePaths = {"detalles", "detalles.producto"})
	Optional<Pedido> findById(Long id);

	@EntityGraph(attributePaths = {"detalles", "detalles.producto"})
	List<Pedido> findAllByOrderByFechaDesc();

	@EntityGraph(attributePaths = {"detalles", "detalles.producto"})
	List<Pedido> findByEstadoOrderByFechaDesc(EstadoPedido estado);
}
