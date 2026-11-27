package com.ttps.security.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Credenciales enviadas para iniciar sesión.
 */
public record LoginRequest(@NotBlank String email, @NotBlank String password) {
}
