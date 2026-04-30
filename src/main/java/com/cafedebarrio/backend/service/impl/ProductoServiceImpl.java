package com.cafedebarrio.backend.service.impl;

import com.cafedebarrio.backend.dto.producto.request.ProductoRequest;
import com.cafedebarrio.backend.dto.producto.response.ProductoDetalleResponse;
import com.cafedebarrio.backend.dto.producto.response.ProductoResponse;
import com.cafedebarrio.backend.entity.Categoria;
import com.cafedebarrio.backend.entity.Producto;
import com.cafedebarrio.backend.exception.ResourceNotFoundException;
import com.cafedebarrio.backend.mapper.ProductoMapper;
import com.cafedebarrio.backend.repository.CategoriaRepository;
import com.cafedebarrio.backend.repository.ProductoRepository;
import com.cafedebarrio.backend.service.ProductoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoServiceImpl implements ProductoService {

	private final ProductoRepository productoRepository;
	private final CategoriaRepository categoriaRepository;
	private final ProductoMapper productoMapper;

	public ProductoServiceImpl(
			ProductoRepository productoRepository,
			CategoriaRepository categoriaRepository,
			ProductoMapper productoMapper
	) {
		this.productoRepository = productoRepository;
		this.categoriaRepository = categoriaRepository;
		this.productoMapper = productoMapper;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ProductoResponse> listarProductos(Long categoriaId, String nombre, Boolean soloActivos, Boolean disponible, Pageable pageable) {
		String nombreNormalizado = nombre == null || nombre.isBlank() ? null : nombre.trim();
		boolean filtrarActivos = soloActivos == null || soloActivos;
		boolean filtrarDisponibles = Boolean.TRUE.equals(disponible);
		Page<Producto> productos = nombreNormalizado == null
				? productoRepository.buscarCatalogoSinNombre(
						categoriaId,
						filtrarActivos,
						filtrarDisponibles,
						pageable
				)
				: productoRepository.buscarCatalogoConNombre(
						categoriaId,
						nombreNormalizado,
						filtrarActivos,
						filtrarDisponibles,
						pageable
				);

		return productos.map(productoMapper::toResponse);
	}

	@Override
	@Transactional(readOnly = true)
	public ProductoDetalleResponse obtenerProductoPorId(Long id) {
		Producto producto = obtenerProductoEntidad(id);
		return productoMapper.toDetalleResponse(producto);
	}

	@Override
	@Transactional
	public ProductoResponse crearProducto(ProductoRequest productoRequest) {
		Categoria categoria = obtenerCategoria(productoRequest.categoriaId());
		Producto producto = productoMapper.toEntity(productoRequest);
		producto.setCategoria(categoria);

		Producto productoGuardado = productoRepository.save(producto);
		return productoMapper.toResponse(productoGuardado);
	}

	@Override
	@Transactional
	public ProductoResponse actualizarProducto(Long id, ProductoRequest productoRequest) {
		Producto producto = obtenerProductoEntidad(id);
		Categoria categoria = obtenerCategoria(productoRequest.categoriaId());

		producto.setNombre(productoRequest.nombre());
		producto.setDescripcion(productoRequest.descripcion());
		producto.setPrecio(productoRequest.precio());
		producto.setStock(productoRequest.stock());
		producto.setImagenUrl(productoRequest.imagenUrl());
		producto.setActivo(productoRequest.activo());
		producto.setCategoria(categoria);

		return productoMapper.toResponse(producto);
	}

	@Override
	@Transactional
	public void desactivarProducto(Long id) {
		Producto producto = obtenerProductoEntidad(id);
		producto.setActivo(false);
	}

	private Producto obtenerProductoEntidad(Long id) {
		return productoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
	}

	private Categoria obtenerCategoria(Long categoriaId) {
		return categoriaRepository.findById(categoriaId)
				.orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con id: " + categoriaId));
	}
}
