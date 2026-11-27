package com.ttps.security.controller;

import com.ttps.security.dto.ExampleResponse;
import com.ttps.security.security.OpenApiConfig;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ejemplos de autorización con permisos y roles.
 */
@RestController
@RequestMapping("/examples")
@SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
public class ExampleController {

  //Valida que el usuario tenga el permiso FOOD_READ para acceder a este endpoint.
  @GetMapping("/food")
  @PreAuthorize("hasAuthority('FOOD_READ')")
  public ExampleResponse readFood() {
    return new ExampleResponse("Ejemplo de lectura de comida");
  }

  //Valida que el usuario tenga el permiso FOOD_WRITE para acceder a este endpoint.
  @PostMapping("/food")
  @PreAuthorize("hasAuthority('FOOD_WRITE')")
  public ExampleResponse writeFood() {
    return new ExampleResponse("Ejemplo de escritura de comida");
  }

  //Valida que el usuario tenga el rol ADMIN para acceder a este endpoint.
  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public ExampleResponse admin() {
    return new ExampleResponse("Ejemplo de acceso de administrador");
  }

  //Valida que el usuario tenga el rol ADMIN o que el UUID del usuario coincida con el UUID del principal autenticado.
  @GetMapping("/user/{uuid}")
  @PreAuthorize("hasRole('ADMIN') or #uuid == principal.id")
  public ExampleResponse getUser(@P("uuid") @PathVariable("uuid") UUID uuid) {
    return new ExampleResponse("Ejemplo de acceso al usuario " + uuid);
  }
}
