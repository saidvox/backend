package com.cafedebarrio.backend.config;

import com.cafedebarrio.backend.entity.Categoria;
import com.cafedebarrio.backend.entity.Producto;
import com.cafedebarrio.backend.repository.CategoriaRepository;
import com.cafedebarrio.backend.repository.ProductoRepository;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

	@Bean
	CommandLineRunner initData(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
		return args -> {
			if (categoriaRepository.count() > 0 || productoRepository.count() > 0) {
				return;
			}

			Categoria cafe = categoriaRepository.save(Categoria.builder().nombre("Cafe").build());
			Categoria kits = categoriaRepository.save(Categoria.builder().nombre("Kits").build());
			Categoria accesorios = categoriaRepository.save(Categoria.builder().nombre("Accesorios").build());

			productoRepository.save(Producto.builder()
					.nombre("Cafe de Altura 250g")
					.descripcion("Cafe en grano de tostado medio con notas a chocolate y frutos secos.")
					.precio(new BigDecimal("25.90"))
					.stock(20)
					.imagenUrl("https://images.unsplash.com/photo-1447933601403-0c6688de566e")
					.activo(true)
					.categoria(cafe)
					.build());

			productoRepository.save(Producto.builder()
					.nombre("Kit Regalo Barista")
					.descripcion("Kit con cafe de especialidad, taza y prensa francesa para regalo.")
					.precio(new BigDecimal("79.90"))
					.stock(8)
					.imagenUrl("https://images.unsplash.com/photo-1495474472287-4d71bcdd2085")
					.activo(true)
					.categoria(kits)
					.build());

			productoRepository.save(Producto.builder()
					.nombre("Molinillo Manual")
					.descripcion("Molinillo manual de acero ajustable para diferentes tipos de molienda.")
					.precio(new BigDecimal("59.50"))
					.stock(12)
					.imagenUrl("https://images.unsplash.com/photo-1517701604599-bb29b565090c")
					.activo(true)
					.categoria(accesorios)
					.build());
		};
	}
}
