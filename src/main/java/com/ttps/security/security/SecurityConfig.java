package com.ttps.security.security;

import com.ttps.security.service.UserDetailsServiceImpl;
import com.ttps.security.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.json.JsonMapper;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  private static final String[] PUBLIC_ENDPOINTS = {"/v3/api-docs", "/v3/api-docs/**", "/v3/api-docs.yaml",
      "/swagger-ui.html", "/swagger-ui/**", "/actuator", "/actuator/**"};

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);
    return new ProviderManager(provider);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtService jwtService,
      UserDetailsServiceImpl userDetailsService, JsonMapper jsonMapper) {
    return http
        // Deshabilita CSRF porque la API no usa cookies para autenticar solicitudes.
        .csrf(AbstractHttpConfigurer::disable)

        // Evita la creación de sesiones en el servidor.
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // Deshabilita CORS; en producción se deben configurar los orígenes permitidos del frontend.
        .cors(AbstractHttpConfigurer::disable)

        // Define qué endpoints son públicos y exige autenticación para el resto.
        .authorizeHttpRequests(authorization -> authorization
            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
            .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
            .anyRequest().authenticated())

        // Agrega el filtro JWT antes del de autenticación por usuario y contraseña.
        .addFilterBefore(new JwtAuthenticationFilter(jwtService, userDetailsService, jsonMapper),
            UsernamePasswordAuthenticationFilter.class)
        .build();
  }
}
