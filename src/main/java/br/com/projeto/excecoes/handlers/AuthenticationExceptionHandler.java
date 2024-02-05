package br.com.projeto.excecoes.handlers;

import br.com.projeto.excecoes.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthenticationExceptionHandler {
  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErroResposta handleBadCredentials() {
    return new ErroResposta("Login ou senha incorretos");
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ErroResposta handleAccessDenied() {
    return new ErroResposta("Você não tem permissão para acessar este recurso");
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErroResposta handleAccessDenied(Exception e) {
    return new ErroResposta(e.getMessage());
  }
}
