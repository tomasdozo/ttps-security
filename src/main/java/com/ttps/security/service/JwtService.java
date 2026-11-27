package com.ttps.security.service;

import com.ttps.security.security.SecurityUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Crea y verifica tokens firmados. El sujeto del token es el UUID del usuario.
 */
@Service
public class JwtService {

    private final SecretKey key;
    private final JwtParser parser;
    private final long expirationMs;

    public JwtService(
            @Value("${security.jwt.secret}") String secretBase64,
            @Value("${security.jwt.expiration-ms}") long expirationMs) {
        if (expirationMs <= 0) {
            throw new IllegalArgumentException("La expiración del JWT debe ser positiva");
        }

        byte[] secretBytes = Base64.getDecoder().decode(secretBase64);
        if (secretBytes.length < 32) {
            throw new IllegalArgumentException("security.jwt.secret debe contener al menos 32 bytes en Base64");
        }
        this.key = Keys.hmacShaKeyFor(secretBytes);
        this.expirationMs = expirationMs;
        this.parser = Jwts.parser()
                .verifyWith(key)
                .build();
    }

    public String createToken(SecurityUser user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("El usuario debe tener un UUID");
        }
        if (user.getUsername().isBlank()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        if (user.getAuthorities().stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Los permisos no pueden ser nulos");
        }

        Instant now = Instant.now();
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getUsername())
                .claim("permissions", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).sorted().toList())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expirationMs)))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Verifica la firma y la expiración del token y devuelve el UUID del sujeto.
     * Lanza JwtException cuando el token no es válido.
     */
    public UUID validateToken(String token) {
        if (token == null || token.isBlank()) {
            throw new JwtException("El token no puede estar vacío");
        }

        Jws<Claims> verifiedToken = parser.parseSignedClaims(token);
        if (!Jwts.SIG.HS256.getId().equals(verifiedToken.getHeader().getAlgorithm())) {
            throw new JwtException("El algoritmo del token debe ser HS256");
        }
        Claims claims = verifiedToken.getPayload();
        if (claims.getExpiration() == null) {
            throw new JwtException("El token debe tener una fecha de expiración");
        }
        String subject = claims.getSubject();
        if (subject == null || subject.isBlank()) {
            throw new JwtException("El token debe tener un sujeto");
        }
        try {
            return UUID.fromString(subject);
        } catch (IllegalArgumentException ex) {
            throw new JwtException("El sujeto del token debe ser un UUID válido", ex);
        }
    }
}
