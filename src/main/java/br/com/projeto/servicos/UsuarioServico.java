package br.com.projeto.servicos;

import br.com.projeto.dtos.DeletarContaDTO;
import br.com.projeto.dtos.MudarSenhaDTO;
import br.com.projeto.dtos.UsuarioDTO;
import br.com.projeto.dtos.UsuarioRespostaDTO;
import br.com.projeto.enums.UsuarioRole;
import br.com.projeto.excecoes.UsuarioException;
import br.com.projeto.excecoes.UsuarioNaoEncontradoException;
import br.com.projeto.modelos.Usuario;
import br.com.projeto.repositorios.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServico implements UserDetailsService {

  private static final String MSG_ERRO_USUARIO_NAO_ENCONTRADO =
      "Não foi encontrado nenhum usuário com o email informado";
  private final UsuarioRepositorio usuarioRepositorio;
  private final PasswordEncoder passwordEncoder;

  public UsuarioRespostaDTO consultarUsuario(String email) {
    Optional<Usuario> usuarioBuscado = this.usuarioRepositorio.findByEmail(email);

    if (usuarioBuscado.isEmpty()) {
      throw new UsuarioNaoEncontradoException();
    }

    Usuario usuario = usuarioBuscado.get();

    return new UsuarioRespostaDTO(
        usuario.getNome(),
        usuario.getEmail(),
        usuario.getDataCadastro(),
        usuario.getDataNascimento());
  }

  @Transactional
  public UserDetails cadastrarUsuario(UsuarioDTO usuarioDTO) {

    if (this.usuarioRepositorio.existsByEmail(usuarioDTO.email())) {
      throw new UsuarioException("O email informado já existe");
    }

    Usuario usuario = new Usuario();
    BeanUtils.copyProperties(usuarioDTO, usuario);
    usuario.setDataCadastro(LocalDate.now());
    usuario.setSenha(this.passwordEncoder.encode(usuarioDTO.senha()));
    usuario.setRole(UsuarioRole.ROLE_USUARIO);

    return this.usuarioRepositorio.save(usuario);
  }

  @Transactional
  public void mudarSenha(String email, MudarSenhaDTO mudarSenhaDTO) {
    this.usuarioRepositorio
        .findByEmail(email)
        .map(
            user -> {
              String senhaAtual = this.passwordEncoder.encode(mudarSenhaDTO.senhaAtual());
              if (user.getSenha().equals(senhaAtual)) {
                user.setSenha(senhaAtual);
                return this.usuarioRepositorio.save(user);
              }
              throw new UsuarioException("Senha atual incorreta");
            })
        .orElseThrow(UsuarioNaoEncontradoException::new);
  }

  @Transactional
  public void tornarAdmin(String email) {
    this.usuarioRepositorio
        .findByEmail(email)
        .map(
            user -> {
              user.setRole(UsuarioRole.ROLE_ADMIN);
              return this.usuarioRepositorio.save(user);
            })
        .orElseThrow(UsuarioNaoEncontradoException::new);
  }

  @Transactional
  public void deletarUsuario(String email, DeletarContaDTO deletarContaDTO) {
    Optional<Usuario> usuario = this.usuarioRepositorio.findByEmail(email);

    if (usuario.isEmpty()) {
      throw new UsuarioNaoEncontradoException();
    }

    if (!usuario.get().getSenha().equals(this.passwordEncoder.encode(deletarContaDTO.senha()))) {
      throw new UsuarioException("Senha incorreta");
    }

    this.usuarioRepositorio.deleteByEmail(email);
  }

  @Transactional
  public void mudarBloqueioConta(String email) {
    this.usuarioRepositorio
        .findByEmail(email)
        .map(
            user -> {
              user.setContaBloqueada(!user.isContaBloqueada());
              return this.usuarioRepositorio.save(user);
            })
        .orElseThrow(UsuarioNaoEncontradoException::new);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.usuarioRepositorio
        .findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException(MSG_ERRO_USUARIO_NAO_ENCONTRADO));
  }
}
