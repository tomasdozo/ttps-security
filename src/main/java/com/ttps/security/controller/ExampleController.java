package com.ttps.security.controller;

import com.ttps.security.dto.ExampleResponse;
import com.ttps.security.security.OpenApiConfig;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Ejemplos de autorización con permisos y roles.
 */
@RestController
@RequestMapping("/examples")
@SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
public class ExampleController {

    // Permite la lectura solo a usuarios con el permiso FOOD_READ.
    @GetMapping("/food")
    @PreAuthorize("hasAuthority('FOOD_READ')")
    public ExampleResponse readFood() {
        return new ExampleResponse("Ejemplo de lectura de comida");
    }

    // Permite la escritura solo a usuarios con el permiso FOOD_WRITE.
    @PostMapping("/food")
    @PreAuthorize("hasAuthority('FOOD_WRITE')")
    public ExampleResponse writeFood() {
        return new ExampleResponse("Ejemplo de escritura de comida");
    }

    // Permite el acceso solo a usuarios con el rol ADMIN.
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ExampleResponse admin() {
        return new ExampleResponse("Ejemplo de acceso de administrador");
    }

    // Permite el acceso a administradores o al usuario cuyo UUID coincide con el principal autenticado.
    @GetMapping("/user/{uuid}")
    @PreAuthorize("hasRole('ADMIN') or #uuid == principal.id")
    public ExampleResponse getUser(@P("uuid") @PathVariable("uuid") UUID uuid) {
        return new ExampleResponse("Ejemplo de acceso al usuario " + uuid);
    }
}
