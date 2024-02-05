package br.com.projeto.enums;

import lombok.Getter;

@Getter
public enum Recursos {
  INTERPRETE_SINAIS("Intérprete de Língua de Sinais disponível"),
  LEGENDAS("Legendas disponíveis em vídeos e anúncios"),
  ALERTAS_VISUAIS("Alertas visuais para eventos sonoros"),
  COMUNICACAO_ALTERNATIVA("Comunicação alternativa disponível"),
  TREINAMENTO_FUNCIONARIOS("Funcionários treinados em comunicação com surdos");

  private final String descricao;

  Recursos(String descricao) {
    this.descricao = descricao;
  }
}
