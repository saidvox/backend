package com.cafedebarrio.backend.config;

import com.cafedebarrio.backend.entity.Categoria;
import com.cafedebarrio.backend.entity.Producto;
import com.cafedebarrio.backend.repository.CategoriaRepository;
import com.cafedebarrio.backend.repository.ProductoRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

	@Bean
	CommandLineRunner initData(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
		return args -> {
			Categoria cafe = findOrCreateCategory(categoriaRepository, "Cafe");
			Categoria kits = findOrCreateCategory(categoriaRepository, "Kits");
			Categoria accesorios = findOrCreateCategory(categoriaRepository, "Accesorios");
			Categoria metodos = findOrCreateCategory(categoriaRepository, "Metodos");
			Categoria reposteria = findOrCreateCategory(categoriaRepository, "Reposteria");
			Categoria merchandising = findOrCreateCategory(categoriaRepository, "Merchandising");

			List<ProductSeed> products = List.of(
					new ProductSeed(
							"Cafe de Altura 250g",
							"Cafe en grano de tostado medio con notas a chocolate y frutos secos.",
							"25.90",
							20,
							"https://images.unsplash.com/photo-1447933601403-0c6688de566e",
							cafe
					),
					new ProductSeed(
							"Cafe Geisha 250g",
							"Cafe de especialidad con perfil floral, acidez brillante y final delicado.",
							"49.90",
							10,
							"https://images.unsplash.com/photo-1509042239860-f550ce710b93",
							cafe
					),
					new ProductSeed(
							"Cafe Bourbon Rojo 340g",
							"Granos seleccionados con notas dulces a panela, caramelo y cacao.",
							"39.50",
							18,
							"https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd",
							cafe
					),
					new ProductSeed(
							"Cafe Honey 250g",
							"Cafe de proceso honey con cuerpo sedoso y notas a miel y frutas maduras.",
							"34.90",
							16,
							"https://images.unsplash.com/photo-1497935586351-b67a49e012bf",
							cafe
					),
					new ProductSeed(
							"Cafe Natural 250g",
							"Cafe de proceso natural con notas intensas a frutos rojos y chocolate.",
							"36.90",
							14,
							"https://images.unsplash.com/photo-1498804103079-a6351b050096",
							cafe
					),
					new ProductSeed(
							"Cafe Descafeinado 250g",
							"Cafe suave, balanceado y libre de cafeina para cualquier momento del dia.",
							"29.90",
							15,
							"https://images.unsplash.com/photo-1461023058943-07fcbe16d735",
							cafe
					),
					new ProductSeed(
							"Cafe Blend de la Casa 500g",
							"Mezcla equilibrada para espresso con notas a chocolate, nueces y crema.",
							"54.90",
							22,
							"https://images.unsplash.com/photo-1517701604599-bb29b565090c",
							cafe
					),
					new ProductSeed(
							"Cafe Cold Brew 500ml",
							"Bebida lista para tomar, preparada en frio durante 18 horas.",
							"18.90",
							25,
							"https://images.unsplash.com/photo-1517701550927-30cf4ba1dba5",
							cafe
					),
					new ProductSeed(
							"Kit Regalo Barista",
							"Kit con cafe de especialidad, taza y prensa francesa para regalo.",
							"79.90",
							8,
							"https://images.unsplash.com/photo-1495474472287-4d71bcdd2085",
							kits
					),
					new ProductSeed(
							"Kit Inicio Espresso",
							"Pack con blend de espresso, tamper, vaso medidor y guia de preparacion.",
							"119.90",
							7,
							"https://images.unsplash.com/photo-1511920170033-f8396924c348",
							kits
					),
					new ProductSeed(
							"Kit V60 Completo",
							"Incluye dripper V60, filtros, jarra y cafe molido para filtrado.",
							"99.90",
							9,
							"https://images.unsplash.com/photo-1521302080334-4bebac2763a6",
							kits
					),
					new ProductSeed(
							"Kit Cold Brew",
							"Botella infusora, cafe molido grueso y receta para preparar cold brew.",
							"89.90",
							11,
							"https://images.unsplash.com/photo-1534778101976-62847782c213",
							kits
					),
					new ProductSeed(
							"Kit Degustacion Origenes",
							"Tres bolsas de cafe de origen para comparar perfiles de sabor.",
							"69.90",
							13,
							"https://images.unsplash.com/photo-1459755486867-b55449bb39ff",
							kits
					),
					new ProductSeed(
							"Kit Oficina Cafetera",
							"Pack pensado para equipos: cafe, filtros, azucar rubia y vasos compostables.",
							"149.90",
							6,
							"https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb",
							kits
					),
					new ProductSeed(
							"Molinillo Manual",
							"Molinillo manual de acero ajustable para diferentes tipos de molienda.",
							"59.50",
							12,
							"https://images.unsplash.com/photo-1517701604599-bb29b565090c",
							accesorios
					),
					new ProductSeed(
							"Balanza Digital Barista",
							"Balanza compacta con precision de 0.1g y temporizador integrado.",
							"74.90",
							10,
							"https://images.unsplash.com/photo-1541167760496-1628856ab772",
							accesorios
					),
					new ProductSeed(
							"Tamper 58mm",
							"Tamper de acero inoxidable con mango ergonomico para espresso.",
							"54.90",
							14,
							"https://images.unsplash.com/photo-1559056199-641a0ac8b55e",
							accesorios
					),
					new ProductSeed(
							"Jarra Lechera 350ml",
							"Jarra de acero para texturizar leche y practicar latte art.",
							"32.90",
							20,
							"https://images.unsplash.com/photo-1534414671319-687fd3f933bd",
							accesorios
					),
					new ProductSeed(
							"Filtros V60 x100",
							"Filtros de papel para preparaciones limpias y consistentes.",
							"24.90",
							30,
							"https://images.unsplash.com/photo-1509042239860-f550ce710b93",
							accesorios
					),
					new ProductSeed(
							"Termometro Barista",
							"Termometro de lectura rapida para controlar leche, agua y extracciones.",
							"22.90",
							18,
							"https://images.unsplash.com/photo-1507133750040-4a8f57021571",
							accesorios
					),
					new ProductSeed(
							"Prensa Francesa 600ml",
							"Cafetera de vidrio resistente para preparar cafe con cuerpo intenso.",
							"64.90",
							12,
							"https://images.unsplash.com/photo-1495474472287-4d71bcdd2085",
							metodos
					),
					new ProductSeed(
							"Dripper V60 Ceramico",
							"Metodo de filtrado para tazas limpias, dulces y aromaticas.",
							"49.90",
							15,
							"https://images.unsplash.com/photo-1521302080334-4bebac2763a6",
							metodos
					),
					new ProductSeed(
							"Aeropress Go",
							"Cafetera portatil para preparar cafe intenso en casa, oficina o viaje.",
							"139.90",
							8,
							"https://images.unsplash.com/photo-1554118811-1e0d58224f24",
							metodos
					),
					new ProductSeed(
							"Moka Italiana 6 Tazas",
							"Cafetera clasica de aluminio para cafe concentrado tipo italiano.",
							"89.90",
							9,
							"https://images.unsplash.com/photo-1557006021-b85faa2bc5e2",
							metodos
					),
					new ProductSeed(
							"Chemex 6 Tazas",
							"Cafetera de vidrio para filtrados claros, elegantes y aromaticos.",
							"159.90",
							5,
							"https://images.unsplash.com/photo-1502462041640-b3d7e50d0662",
							metodos
					),
					new ProductSeed(
							"Brownie de Cafe",
							"Brownie humedo con cacao intenso y reduccion de espresso.",
							"12.90",
							24,
							"https://images.unsplash.com/photo-1606313564200-e75d5e30476c",
							reposteria
					),
					new ProductSeed(
							"Cheesecake de Espresso",
							"Porcion de cheesecake cremoso con base de galleta y cafe.",
							"16.90",
							18,
							"https://images.unsplash.com/photo-1533134242443-d4fd215305ad",
							reposteria
					),
					new ProductSeed(
							"Alfajor de Cafe",
							"Alfajor artesanal relleno con manjar y toque de cafe.",
							"7.90",
							35,
							"https://images.unsplash.com/photo-1551024506-0bccd828d307",
							reposteria
					),
					new ProductSeed(
							"Muffin de Chocolate y Cafe",
							"Muffin esponjoso con chips de chocolate y aroma de espresso.",
							"9.90",
							28,
							"https://images.unsplash.com/photo-1607958996333-41aef7caefaa",
							reposteria
					),
					new ProductSeed(
							"Cookie de Avena y Cafe",
							"Galleta crocante de avena con notas de cafe tostado.",
							"6.90",
							40,
							"https://images.unsplash.com/photo-1499636136210-6f4ee915583e",
							reposteria
					),
					new ProductSeed(
							"Taza Cafe de Barrio",
							"Taza ceramica de 300ml con acabado mate y logo de la marca.",
							"29.90",
							25,
							"https://images.unsplash.com/photo-1514228742587-6b1558fcca3d",
							merchandising
					),
					new ProductSeed(
							"Termo Reutilizable 450ml",
							"Termo de acero para bebidas calientes y frias con tapa antiderrame.",
							"69.90",
							16,
							"https://images.unsplash.com/photo-1523362628745-0c100150b504",
							merchandising
					),
					new ProductSeed(
							"Bolsa Tote Barista",
							"Bolsa de tela resistente para compras, cafe y accesorios.",
							"34.90",
							20,
							"https://images.unsplash.com/photo-1542291026-7eec264c27ff",
							merchandising
					),
					new ProductSeed(
							"Delantal Cafe de Barrio",
							"Delantal de lona con bolsillos frontales para baristas y cocina.",
							"59.90",
							12,
							"https://images.unsplash.com/photo-1556909114-f6e7ad7d3136",
							merchandising
					),
					new ProductSeed(
							"Gift Card S/ 50",
							"Tarjeta de regalo digital para canjear por productos del catalogo.",
							"50.00",
							100,
							"https://images.unsplash.com/photo-1512909006721-3d6018887383",
							merchandising
					)
			);

			for (ProductSeed product : products) {
				createProductIfMissing(productoRepository, product);
			}
		};
	}

	private Categoria findOrCreateCategory(CategoriaRepository categoriaRepository, String nombre) {
		return categoriaRepository.findByNombreIgnoreCase(nombre)
				.orElseGet(() -> categoriaRepository.save(Categoria.builder().nombre(nombre).build()));
	}

	private void createProductIfMissing(ProductoRepository productoRepository, ProductSeed product) {
		if (productoRepository.existsByNombreIgnoreCase(product.nombre())) {
			return;
		}

		productoRepository.save(Producto.builder()
				.nombre(product.nombre())
				.descripcion(product.descripcion())
				.precio(new BigDecimal(product.precio()))
				.stock(product.stock())
				.imagenUrl(product.imagenUrl())
				.activo(true)
				.categoria(product.categoria())
				.build());
	}

	private record ProductSeed(
			String nombre,
			String descripcion,
			String precio,
			Integer stock,
			String imagenUrl,
			Categoria categoria
	) {
	}
}
