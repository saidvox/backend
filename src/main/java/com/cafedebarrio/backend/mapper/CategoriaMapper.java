package com.cafedebarrio.backend.mapper;

import com.cafedebarrio.backend.dto.categoria.request.CategoriaRequest;
import com.cafedebarrio.backend.dto.categoria.response.CategoriaResponse;
import com.cafedebarrio.backend.entity.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoriaMapper {

	CategoriaResponse toResponse(Categoria categoria);

	@Mapping(target = "id", ignore = true)
	Categoria toEntity(CategoriaRequest categoriaRequest);
}
