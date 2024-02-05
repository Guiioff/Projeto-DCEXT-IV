package br.com.projeto.excecoes;

public class NaoEncontradoException extends RuntimeException {
  public NaoEncontradoException(String message) {
    super(message);
  }
}
