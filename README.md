# Cafe de Barrio Backend

Backend REST de `Cafe de Barrio` construido con Spring Boot 4, JPA, MapStruct, Swagger y JWT.

## Stack

- Java 21
- Spring Boot 4
- Spring Security + JWT Bearer
- Spring Data JPA
- H2 para `local`
- PostgreSQL para `docker`
- Swagger UI

## Perfiles

- `local`: corre desde tu IDE o Maven con H2 en memoria.
- `docker`: corre en contenedores y usa PostgreSQL.

## Levantar en local

```bash
mvn spring-boot:run
```

Swagger:

- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Login JWT

Endpoint:

- `POST /api/auth/login`

Credenciales por defecto:

- usuario: `admin`
- clave: `Admin12345*`

Puedes cambiarlas con variables de entorno:

- `APP_ADMIN_USERNAME`
- `APP_ADMIN_PASSWORD`
- `JWT_SECRET`

## Rutas publicas

- `GET /api/categorias`
- `GET /api/productos`
- `GET /api/productos?categoria=1&disponible=true`
- `GET /api/productos/{id}`
- `POST /api/pedidos`
- `POST /api/auth/login`

## Rutas protegidas con `ROLE_ADMIN`

- `POST /api/productos`
- `PUT /api/productos/{id}`
- `DELETE /api/productos/{id}`
- `GET /api/pedidos`
- `PATCH /api/pedidos/{id}/estado`

## Header para endpoints protegidos

```http
Authorization: Bearer TU_TOKEN
```

## Tests

```bash
mvn test
```

## Coleccion de endpoints

El archivo `api-collection.http` incluye ejemplos de las rutas publicas y administrativas.
