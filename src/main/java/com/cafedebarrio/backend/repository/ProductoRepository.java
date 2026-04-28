package com.cafedebarrio.backend.repository;

import com.cafedebarrio.backend.entity.Producto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

	boolean existsByNombreIgnoreCase(String nombre);

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

	@EntityGraph(attributePaths = "categoria")
	@Query("""
			select p
			from Producto p
			where (:categoriaId is null or p.categoria.id = :categoriaId)
			  and (:nombre is null or lower(p.nombre) like lower(concat('%', :nombre, '%')))
			  and (:soloActivos = false or p.activo = true)
			  and (:disponible = false or p.stock > 0)
			order by p.nombre asc
			""")
	Page<Producto> buscarCatalogo(
			@Param("categoriaId") Long categoriaId,
			@Param("nombre") String nombre,
			@Param("soloActivos") boolean soloActivos,
			@Param("disponible") boolean disponible,
			Pageable pageable
	);
}
