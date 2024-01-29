package br.com.projeto.excecoes;

public class UsuarioException extends RuntimeException {
  public UsuarioException(String mensagem) {
    super(mensagem);
  }
}
