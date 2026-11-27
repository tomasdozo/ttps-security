package com.ttps.security.exception;

import com.ttps.security.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Centraliza los errores esperados que ocurren al procesar las peticiones.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({BadCredentialsException.class, AccountStatusException.class})
  public ResponseEntity<ErrorResponse> handleInvalidCredentials() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(new ErrorResponse("Credenciales inválidas"));
  }

  @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class,
      MethodArgumentTypeMismatchException.class})
  public ResponseEntity<ErrorResponse> handleInvalidRequest() {
    return ResponseEntity.badRequest()
        .body(new ErrorResponse("La petición es inválida"));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDenied() {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(new ErrorResponse("Acceso denegado"));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleNotExpectedRequest() {
    return ResponseEntity.internalServerError()
        .body(new ErrorResponse("Hubo un error inesperado"));
  }
}
