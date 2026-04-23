package com.cafedebarrio.backend.repository;

import com.cafedebarrio.backend.entity.Producto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

	@Override
	@EntityGraph(attributePaths = "categoria")
	List<Producto> findAll();

	@Override
	@EntityGraph(attributePaths = "categoria")
	Optional<Producto> findById(Long id);

	@EntityGraph(attributePaths = "categoria")
	List<Producto> findByActivoTrueOrderByNombreAsc();

	@EntityGraph(attributePaths = "categoria")
	List<Producto> findByCategoriaIdAndActivoTrueOrderByNombreAsc(Long categoriaId);
}
