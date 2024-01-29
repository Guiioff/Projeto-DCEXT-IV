package br.com.projeto.excecoes;

public class UsuarioNaoEncontradoException extends RuntimeException {
  public UsuarioNaoEncontradoException() {
    super("Não foi encontrado nenhum usuário com o email informado");
  }
}
