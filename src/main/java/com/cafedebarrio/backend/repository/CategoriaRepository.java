package com.cafedebarrio.backend.repository;

import com.cafedebarrio.backend.entity.Categoria;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	boolean existsByNombreIgnoreCase(String nombre);

	Optional<Categoria> findByNombreIgnoreCase(String nombre);
}
