package br.com.projeto.excecoes.handlers;

import br.com.projeto.excecoes.ErroResposta;
import br.com.projeto.excecoes.UsuarioNaoEncontradoException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultExceptionHandler {
  @ExceptionHandler(UsuarioNaoEncontradoException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErroResposta handleUsuarioNaoEncontradoException(UsuarioNaoEncontradoException exception) {
    return new ErroResposta(exception.getMessage());
  }

  @ExceptionHandler(ExpiredJwtException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErroResposta handleExpiredJwt() {
    return new ErroResposta("Seu token JWT está expirado. Faça login novamente");
  }
}
