package com.ttps.security.entity;

import com.ttps.security.security.Permission;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
