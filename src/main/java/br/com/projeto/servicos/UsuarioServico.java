package br.com.projeto.servicos;

import br.com.projeto.dtos.DeletarContaDTO;
import br.com.projeto.dtos.MudarSenhaDTO;
import br.com.projeto.dtos.UsuarioDTO;
import br.com.projeto.dtos.UsuarioRespostaDTO;
import br.com.projeto.enums.UsuarioRole;
import br.com.projeto.excecoes.UsuarioException;
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

import java.util.Date;
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
      throw new UsuarioException(MSG_ERRO_USUARIO_NAO_ENCONTRADO);
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
    usuario.setDataCadastro(new Date());
    usuario.setSenha(this.passwordEncoder.encode(usuarioDTO.senha()));
    usuario.setRole(UsuarioRole.ROLE_USUARIO);

    return this.usuarioRepositorio.save(usuario);
  }

  @Transactional
  public void mudarSenha(String email, MudarSenhaDTO mudarSenhaDTO) {
    Optional<Usuario> usuario = this.usuarioRepositorio.findByEmail(email);

    if (usuario.isEmpty()) {
      throw new UsuarioException(MSG_ERRO_USUARIO_NAO_ENCONTRADO);
    }

    if (!usuario.get().getSenha().equals(this.passwordEncoder.encode(mudarSenhaDTO.senhaAtual()))) {
      throw new UsuarioException("Senha atual incorreta");
    }

    Usuario usuarioAtualizado = usuario.get();
    usuarioAtualizado.setSenha(this.passwordEncoder.encode(mudarSenhaDTO.novaSenha()));

    this.usuarioRepositorio.save(usuarioAtualizado);
  }

  @Transactional
  public void tornarAdmin(String email) {
    Optional<Usuario> usuario = this.usuarioRepositorio.findByEmail(email);

    if (usuario.isEmpty()) {
      throw new UsuarioException(MSG_ERRO_USUARIO_NAO_ENCONTRADO);
    }

    Usuario usuarioAtualizado = usuario.get();
    usuarioAtualizado.setRole(UsuarioRole.ROLE_ADMIN);

    this.usuarioRepositorio.save(usuarioAtualizado);
  }

  @Transactional
  public void deletarUsuario(String email, DeletarContaDTO deletarContaDTO) {
    Optional<Usuario> usuario = this.usuarioRepositorio.findByEmail(email);

    if (usuario.isEmpty()) {
      throw new UsuarioException(MSG_ERRO_USUARIO_NAO_ENCONTRADO);
    }

    if (!usuario.get().getSenha().equals(this.passwordEncoder.encode(deletarContaDTO.senha()))) {
      throw new UsuarioException("Senha incorreta");
    }

    this.usuarioRepositorio.deleteByEmail(email);
  }

  @Transactional
  public void mudarBloqueioConta(String email) {
    Optional<Usuario> usuarioBuscado = this.usuarioRepositorio.findByEmail(email);

    if (usuarioBuscado.isEmpty()) {
      throw new UsuarioException(MSG_ERRO_USUARIO_NAO_ENCONTRADO);
    }

    Usuario usuario = usuarioBuscado.get();
    usuario.setContaBloqueada(!usuario.isContaBloqueada());

    this.usuarioRepositorio.save(usuario);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.usuarioRepositorio
        .findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException(MSG_ERRO_USUARIO_NAO_ENCONTRADO));
  }
}
