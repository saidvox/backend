package com.cafedebarrio.backend.service;

import com.cafedebarrio.backend.dto.producto.request.ProductoRequest;
import com.cafedebarrio.backend.dto.producto.response.ProductoDetalleResponse;
import com.cafedebarrio.backend.dto.producto.response.ProductoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductoService {

	Page<ProductoResponse> listarProductos(Long categoriaId, Boolean soloActivos, Boolean disponible, Pageable pageable);

	ProductoDetalleResponse obtenerProductoPorId(Long id);

	ProductoResponse crearProducto(ProductoRequest productoRequest);

	ProductoResponse actualizarProducto(Long id, ProductoRequest productoRequest);

	void desactivarProducto(Long id);
}
