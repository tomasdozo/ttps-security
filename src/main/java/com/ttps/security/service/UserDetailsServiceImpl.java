package com.ttps.security.service;

import com.ttps.security.repository.UserRepository;
import com.ttps.security.security.SecurityUser;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Busca usuarios por correo electrónico para la autenticación de Spring Security.
 * Implementa UserDetailsService, que Spring Security utiliza para obtener los datos del usuario durante la autenticación.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  @Transactional(readOnly = true)
  @NonNull
  public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
        .map(SecurityUser::new)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
  }

  @Transactional(readOnly = true)
  public Optional<SecurityUser> findById(UUID id) {
    return userRepository.findById(id).map(SecurityUser::new);
  }
}
