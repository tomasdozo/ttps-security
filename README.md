# Spring Security y JWT

Aplicación de ejemplo que autentica usuarios con JWT y protege endpoints mediante permisos y roles. Usa una base de datos H2 en memoria.

## Uso

1. Con Java 25 instalado, ejecutá `./mvnw spring-boot:run`.
2. Abrí [Swagger UI](http://localhost:8080/swagger-ui/index.html) y llamá a `POST /auth/login` con `{"email":"admin@ejemplo.com","password":"1234"}`.
3. Copiá el token de la respuesta en **Authorize**, sin el prefijo `Bearer`, y probá los endpoints de `/examples`. Swagger muestra los demás usuarios y sus permisos.

Las credenciales y la clave JWT son solo para este ejemplo; no deben usarse en producción.