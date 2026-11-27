package com.ttps.security.service;

import com.ttps.security.dto.LoginRequest;
import com.ttps.security.dto.LoginResponse;
import com.ttps.security.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  @Transactional(readOnly = true)
  public LoginResponse login(LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
        UsernamePasswordAuthenticationToken.unauthenticated(request.email(), request.password()));
    SecurityUser principal = (SecurityUser) authentication.getPrincipal();
    return new LoginResponse(jwtService.createToken(principal));
  }
}
