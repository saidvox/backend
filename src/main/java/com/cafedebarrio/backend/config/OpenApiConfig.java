package com.cafedebarrio.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
		name = "bearer-jwt",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat = "JWT",
		description = "Autenticacion JWT para endpoints administrativos"
)
@OpenAPIDefinition(
		info = @Info(
				title = "Cafe de Barrio API",
				version = "v1",
				description = "API REST para gestionar categorias, productos, pedidos y autenticacion administrativa del mini e-commerce Cafe de Barrio.",
				contact = @Contact(name = "Cafe de Barrio")
		),
		servers = {
				@Server(url = "http://localhost:8080", description = "Entorno local")
		}
)
public class OpenApiConfig {
}
