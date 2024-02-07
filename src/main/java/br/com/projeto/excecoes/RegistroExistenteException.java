package br.com.projeto.excecoes;

public class RegistroExistenteException extends RuntimeException {
  public RegistroExistenteException(String message) {
    super(message);
  }
}
