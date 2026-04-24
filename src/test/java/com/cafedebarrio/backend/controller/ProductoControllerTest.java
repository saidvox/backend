package com.cafedebarrio.backend.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cafedebarrio.backend.dto.producto.request.ProductoRequest;
import com.cafedebarrio.backend.dto.producto.response.ProductoResponse;
import com.cafedebarrio.backend.exception.GlobalExceptionHandler;
import com.cafedebarrio.backend.exception.ResourceNotFoundException;
import com.cafedebarrio.backend.service.ProductoService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.Mockito.when;

@WebMvcTest(ProductoController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class ProductoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private ProductoService productoService;

	@Test
	@DisplayName("Should return product list")
	void shouldReturnProductList() throws Exception {
		when(productoService.listarProductos(null, null)).thenReturn(List.of(
				new ProductoResponse(
						1L,
						"Cafe de Altura 250g",
						"Cafe en grano de tostado medio con notas a chocolate.",
						new BigDecimal("25.90"),
						20,
						"https://images.unsplash.com/photo-1447933601403-0c6688de566e",
						true,
						1L,
						"Cafe")));

		mockMvc.perform(get("/api/productos"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].nombre").value("Cafe de Altura 250g"))
				.andExpect(jsonPath("$[0].categoriaNombre").value("Cafe"));
	}

	@Test
	@DisplayName("Should return not found when product does not exist")
	void shouldReturnNotFoundWhenProductDoesNotExist() throws Exception {
		when(productoService.obtenerProductoPorId(99L))
				.thenThrow(new ResourceNotFoundException("Producto no encontrado con id: 99"));

		mockMvc.perform(get("/api/productos/99"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Producto no encontrado con id: 99"));
	}

	@Test
	@DisplayName("Should create product when request is valid")
	@WithMockUser(roles = "ADMIN")
	void shouldCreateProductWhenRequestIsValid() throws Exception {
		ProductoRequest request = new ProductoRequest(
				"Cafe Geisha 340g",
				"Cafe de especialidad con notas florales y citricas.",
				new BigDecimal("45.50"),
				6,
				"https://images.unsplash.com/photo-1509042239860-f550ce710b93",
				true,
				1L);

		ProductoResponse response = new ProductoResponse(
				4L,
				request.nombre(),
				request.descripcion(),
				request.precio(),
				request.stock(),
				request.imagenUrl(),
				request.activo(),
				request.categoriaId(),
				"Cafe");

		when(productoService.crearProducto(request)).thenReturn(response);

		String requestBody = """
				{
				  "nombre": "Cafe Geisha 340g",
				  "descripcion": "Cafe de especialidad con notas florales y citricas.",
				  "precio": 45.50,
				  "stock": 6,
				  "imagenUrl": "https://images.unsplash.com/photo-1509042239860-f550ce710b93",
				  "activo": true,
				  "categoriaId": 1
				}
				""";

		mockMvc.perform(post("/api/productos")
				.with(csrf())
				.contentType("application/json")
				.content(requestBody))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(4))
				.andExpect(jsonPath("$.nombre").value("Cafe Geisha 340g"))
				.andExpect(jsonPath("$.categoriaNombre").value("Cafe"));
	}

	@Test
	@DisplayName("Should return bad request when product request is invalid")
	@WithMockUser(roles = "ADMIN")
	void shouldReturnBadRequestWhenProductRequestIsInvalid() throws Exception {
		String invalidBody = """
				{
				  "nombre": "",
				  "descripcion": "",
				  "precio": 0,
				  "stock": -1,
				  "activo": true
				}
				""";

		mockMvc.perform(post("/api/productos")
				.with(csrf())
				.contentType("application/json")
				.content(invalidBody))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Error de validacion en la solicitud"))
				.andExpect(jsonPath("$.details").isArray());
	}
}
