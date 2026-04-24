package com.cafedebarrio.backend.service;

import com.cafedebarrio.backend.dto.categoria.response.CategoriaResponse;
import java.util.List;

public interface CategoriaService {

	List<CategoriaResponse> listarCategorias();
}
