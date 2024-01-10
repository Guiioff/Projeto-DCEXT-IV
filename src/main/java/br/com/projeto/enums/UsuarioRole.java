package br.com.projeto.enums;

import lombok.Getter;

@Getter
public enum UsuarioRole {
  ROLE_ADMIN("Administrador do sistema"),
  ROLE_USUARIO("Usuário padrão do sistema");

  private final String descricao;

  UsuarioRole(String descricao) {
    this.descricao = descricao;
  }
}
