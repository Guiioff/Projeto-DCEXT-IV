package br.com.projeto.servicos;

import br.com.projeto.dtos.UsuarioDTO;
import br.com.projeto.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServico {

  @Autowired private UsuarioRepositorio usuarioRepositorio;

  public void cadastrarUsuario(UsuarioDTO usuarioDTO) throws RuntimeException {

    if (usuarioDTO.nome() == null || usuarioDTO.nome().isBlank()) {
      throw new RuntimeException("O nome do usuário não pode ser nulo ou vazio");
    }

    if (usuarioDTO.email() == null || usuarioDTO.email().isBlank()) {
      throw new RuntimeException("O email do usuário não pode ser nulo ou vazio");
    }

    if (usuarioRepositorio.findByEmail(usuarioDTO.email()).isPresent()) {
      throw new RuntimeException("O email informado já existe");
    }

    if (usuarioDTO.senha() == null || usuarioDTO.senha().isBlank()) {
      throw new RuntimeException("A senha do usuário não pode ser nula ou vazia");
    }

    if (usuarioDTO.senha().length() < 8) {
      throw new RuntimeException("A senha do usuário deve ter, no mínimo, 8 caracteres");
    }

    if (usuarioDTO.dataNascimento() == null) {
      throw new RuntimeException("A data de nascimento do usuário não pode ser nula");
    }
  }
}
