package br.com.projeto.excecoes;

import lombok.Getter;

import java.util.List;

@Getter
public class ErroResposta {
  private final List<String> erros;

  public ErroResposta(String mensagemErro) {
    this.erros = List.of(mensagemErro);
  }

  public ErroResposta(List<String> listaErros) {
    this.erros = listaErros;
  }
}
