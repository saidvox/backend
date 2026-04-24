package com.cafedebarrio.backend.mapper;

import com.cafedebarrio.backend.dto.producto.request.ProductoRequest;
import com.cafedebarrio.backend.dto.producto.response.ProductoDetalleResponse;
import com.cafedebarrio.backend.dto.producto.response.ProductoResponse;
import com.cafedebarrio.backend.entity.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductoMapper {

	@Mapping(target = "categoriaId", source = "categoria.id")
	@Mapping(target = "categoriaNombre", source = "categoria.nombre")
	ProductoResponse toResponse(Producto producto);

	@Mapping(target = "categoriaId", source = "categoria.id")
	@Mapping(target = "categoriaNombre", source = "categoria.nombre")
	ProductoDetalleResponse toDetalleResponse(Producto producto);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "categoria", ignore = true)
	Producto toEntity(ProductoRequest productoRequest);
}
