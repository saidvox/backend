package com.cafedebarrio.backend.mapper;

import com.cafedebarrio.backend.dto.pedido.request.PedidoRequest;
import com.cafedebarrio.backend.dto.pedido.response.PedidoResponse;
import com.cafedebarrio.backend.entity.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
		componentModel = "spring",
		unmappedTargetPolicy = ReportingPolicy.IGNORE,
		uses = DetallePedidoMapper.class
)
public interface PedidoMapper {

	PedidoResponse toResponse(Pedido pedido);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "fecha", ignore = true)
	@Mapping(target = "estado", ignore = true)
	@Mapping(target = "total", ignore = true)
	@Mapping(target = "detalles", ignore = true)
	Pedido toEntity(PedidoRequest pedidoRequest);
}
