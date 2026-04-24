package com.cafedebarrio.backend.service;

import com.cafedebarrio.backend.dto.producto.request.ProductoRequest;
import com.cafedebarrio.backend.dto.producto.response.ProductoDetalleResponse;
import com.cafedebarrio.backend.dto.producto.response.ProductoResponse;
import java.util.List;

public interface ProductoService {

	List<ProductoResponse> listarProductos(Long categoriaId, Boolean soloActivos);

	ProductoDetalleResponse obtenerProductoPorId(Long id);

	ProductoResponse crearProducto(ProductoRequest productoRequest);

	ProductoResponse actualizarProducto(Long id, ProductoRequest productoRequest);

	void desactivarProducto(Long id);
}
