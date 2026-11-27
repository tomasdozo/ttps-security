package com.ttps.security.entity;

import com.ttps.security.security.Permission;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

/**
 * Representa un usuario de la aplicación, independiente de Spring Security.
 */
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    // Contraseña almacenada como hash.
    @Column(nullable = false)
    private String password;

    // Permisos y roles asignados al usuario.
    @Column(nullable = false)
    private Set<Permission> permissions;

}
