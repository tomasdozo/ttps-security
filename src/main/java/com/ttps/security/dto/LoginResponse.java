package com.ttps.security.dto;

/**
 * Token devuelto después de autenticar las credenciales.
 */
public record LoginResponse(String token) {
}
