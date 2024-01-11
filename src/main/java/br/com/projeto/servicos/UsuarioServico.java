package br.com.projeto.servicos;

import br.com.projeto.dtos.UsuarioDTO;
import br.com.projeto.enums.UsuarioRole;
import br.com.projeto.modelos.Usuario;
import br.com.projeto.repositorios.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UsuarioServico implements UserDetailsService {

  private final UsuarioRepositorio usuarioRepositorio;
  private final PasswordEncoder passwordEncoder;

  public UserDetails cadastrarUsuario(UsuarioDTO usuarioDTO) {

    if (this.usuarioRepositorio.existsByEmail(usuarioDTO.email())) {
      throw new IllegalArgumentException("O email informado já existe");
    }

    Usuario usuario = new Usuario();
    BeanUtils.copyProperties(usuarioDTO, usuario);
    usuario.setDataCadastro(new Date());
    usuario.setSenha(this.passwordEncoder.encode(usuarioDTO.senha()));
    usuario.setRole(UsuarioRole.ROLE_ADMIN);

    return this.usuarioRepositorio.save(usuario);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.usuarioRepositorio
        .findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("O usuário não foi encontrado"));
  }
}
