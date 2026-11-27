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
 * El usuario estandar de la aplicacion. No lleva ningun agregado de Spring Security.
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

  // La contraseña encriptada.
  @Column(nullable = false)
  private String password;

  // Cada usuario tiene un set de permisos y/o roles.
  @Column(nullable = false)
  private Set<Permission> permissions;

}
