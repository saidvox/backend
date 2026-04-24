package com.cafedebarrio.backend.service.impl;

import com.cafedebarrio.backend.dto.categoria.response.CategoriaResponse;
import com.cafedebarrio.backend.mapper.CategoriaMapper;
import com.cafedebarrio.backend.repository.CategoriaRepository;
import com.cafedebarrio.backend.service.CategoriaService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	private final CategoriaRepository categoriaRepository;
	private final CategoriaMapper categoriaMapper;

	public CategoriaServiceImpl(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
		this.categoriaRepository = categoriaRepository;
		this.categoriaMapper = categoriaMapper;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CategoriaResponse> listarCategorias() {
		return categoriaRepository.findAll()
				.stream()
				.map(categoriaMapper::toResponse)
				.toList();
	}
}
