package com.cafedebarrio.backend.entity;

import com.cafedebarrio.backend.enums.EstadoPedido;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
		name = "pedidos",
		indexes = {
				@Index(name = "idx_pedido_estado", columnList = "estado"),
				@Index(name = "idx_pedido_fecha", columnList = "fecha")
		}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 150)
	private String clienteNombre;

	@Column(nullable = false, length = 20)
	private String celular;

	@Column(nullable = false, length = 255)
	private String direccion;

	@Column(nullable = false)
	private LocalDateTime fecha;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private EstadoPedido estado;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal total;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<DetallePedido> detalles = new ArrayList<>();

	@PrePersist
	public void prePersist() {
		if (fecha == null) {
			fecha = LocalDateTime.now();
		}
		if (estado == null) {
			estado = EstadoPedido.PENDIENTE;
		}
		if (total == null) {
			total = BigDecimal.ZERO;
		}
	}

	public void addDetalle(DetallePedido detallePedido) {
		detalles.add(detallePedido);
		detallePedido.setPedido(this);
	}

	public void removeDetalle(DetallePedido detallePedido) {
		detalles.remove(detallePedido);
		detallePedido.setPedido(null);
	}
}
