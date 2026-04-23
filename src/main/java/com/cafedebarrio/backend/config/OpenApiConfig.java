package com.cafedebarrio.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
		info = @Info(
				title = "Cafe de Barrio API",
				version = "v1",
				description = "API REST para catalogo, productos y pedidos del mini e-commerce Cafe de Barrio.",
				contact = @Contact(name = "Cafe de Barrio")
		)
)
public class OpenApiConfig {
}
