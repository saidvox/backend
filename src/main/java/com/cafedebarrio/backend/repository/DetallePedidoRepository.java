package com.cafedebarrio.backend.repository;

import com.cafedebarrio.backend.entity.DetallePedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

	List<DetallePedido> findByPedidoId(Long pedidoId);
}
