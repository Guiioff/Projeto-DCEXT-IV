package br.com.projeto.excecoes.handlers;

import br.com.projeto.dtos.ErrorResponseDTO;
import br.com.projeto.excecoes.UsuarioException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BadCredentialsException.class)
  @ResponseBody
  public ResponseEntity<ErrorResponseDTO> handleBadCredentials() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(new ErrorResponseDTO(HttpStatus.UNAUTHORIZED.value(), "Login ou senha incorretos"));
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseBody
  public ResponseEntity<ErrorResponseDTO> handleAccessDenied() {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(
            new ErrorResponseDTO(
                HttpStatus.FORBIDDEN.value(), "Você não pode acessar esse recurso"));
  }

  @ExceptionHandler(ExpiredJwtException.class)
  @ResponseBody
  public ResponseEntity<ErrorResponseDTO> handleExpiredJwt() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            new ErrorResponseDTO(
                HttpStatus.UNAUTHORIZED.value(),
                "Seu token JWT está expirado. Faça login novamente"));
  }

  @ExceptionHandler(UsuarioException.class)
  @ResponseBody
  public ResponseEntity<ErrorResponseDTO> handleUsuarioException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
  }
}
