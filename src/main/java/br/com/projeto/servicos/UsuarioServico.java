package br.com.projeto.servicos;

import br.com.projeto.dtos.UsuarioDTO;
import br.com.projeto.enums.UsuarioRole;
import br.com.projeto.modelos.Usuario;
import br.com.projeto.repositorios.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UsuarioServico {

  private final UsuarioRepositorio usuarioRepositorio;

  public UserDetails cadastrarUsuario(UsuarioDTO usuarioDTO) {

    if (this.usuarioRepositorio.existsByEmail(usuarioDTO.email())) {
      throw new IllegalArgumentException("O email informado já existe");
    }

    Usuario usuario = new Usuario();
    BeanUtils.copyProperties(usuarioDTO, usuario);
    usuario.setDataCadastro(new Date());
    usuario.setRole(UsuarioRole.ROLE_ADMIN);

    return this.usuarioRepositorio.save(usuario);
  }
}
