package com.ttps.security.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Configura Swagger UI para enviar el JWT como token Bearer.
 */
@Configuration
@OpenAPIDefinition(info = @Info(title = "API de ejemplo de seguridad y JWT", version = "1.0",
        description = """
                Usuarios de ejemplo (contraseña: `1234`):
                
                - `lector@ejemplo.com`: `FOOD_READ`.
                - `editor@ejemplo.com`: `FOOD_READ`, `FOOD_WRITE`.
                - `admin@ejemplo.com`: `FOOD_READ`, `FOOD_WRITE`, `ROLE_ADMIN`.
                
                Estas credenciales son únicamente para el ejemplo y no deben usarse en producción.
                """))
@SecurityScheme(name = OpenApiConfig.BEARER_AUTH, type = SecuritySchemeType.HTTP,
        scheme = "bearer", bearerFormat = "JWT")
public class OpenApiConfig {

    public static final String BEARER_AUTH = "bearerAuth";
}
