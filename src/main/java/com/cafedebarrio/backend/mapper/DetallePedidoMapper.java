package com.cafedebarrio.backend.mapper;

import com.cafedebarrio.backend.dto.pedido.response.DetallePedidoResponse;
import com.cafedebarrio.backend.entity.DetallePedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DetallePedidoMapper {

	@Mapping(target = "productoId", source = "producto.id")
	@Mapping(target = "productoNombre", source = "producto.nombre")
	DetallePedidoResponse toResponse(DetallePedido detallePedido);
}
