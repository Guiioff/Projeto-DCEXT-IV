package br.com.projeto.excecoes;

public class SenhaIncorretaException extends RuntimeException {
  public SenhaIncorretaException(String message) {
    super(message);
  }
}
