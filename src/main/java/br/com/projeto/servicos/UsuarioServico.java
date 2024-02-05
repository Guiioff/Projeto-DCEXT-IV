package br.com.projeto.servicos;

import br.com.projeto.dtos.DeletarContaDTO;
import br.com.projeto.dtos.MudarSenhaDTO;
import br.com.projeto.dtos.UsuarioDTO;
import br.com.projeto.dtos.UsuarioRespostaDTO;
import br.com.projeto.enums.UsuarioRole;
import br.com.projeto.excecoes.SenhaIncorretaException;
import br.com.projeto.excecoes.RegistroExistenteException;
import br.com.projeto.excecoes.NaoEncontradoException;
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

@Service
@RequiredArgsConstructor
public class UsuarioServico implements UserDetailsService {

  private static final String MSG_ERRO_USUARIO_NAO_ENCONTRADO =
      "Não foi encontrado nenhum usuário com o nome informado";
  private final UsuarioRepositorio usuarioRepositorio;
  private final PasswordEncoder passwordEncoder;

  public UsuarioRespostaDTO consultarUsuario(String nomeUsuario) {
    Usuario usuario =
        this.usuarioRepositorio
            .findByNomeUsuario(nomeUsuario)
            .orElseThrow(() -> new NaoEncontradoException(MSG_ERRO_USUARIO_NAO_ENCONTRADO));

    return new UsuarioRespostaDTO(
        usuario.getNomeUsuario(),
        usuario.getEmail(),
        usuario.getDataCadastro(),
        usuario.getDataNascimento());
  }

  @Transactional
  public UserDetails cadastrarUsuario(UsuarioDTO usuarioDTO) {

    if (this.usuarioRepositorio.existsByEmail(usuarioDTO.email())) {
      throw new RegistroExistenteException("O email informado já existe");
    }

    if (this.usuarioRepositorio.existsByNomeUsuario(usuarioDTO.nomeUsuario())) {
      throw new RegistroExistenteException("O nome de usuário informado já existe");
    }

    Usuario usuario = new Usuario();
    BeanUtils.copyProperties(usuarioDTO, usuario);
    usuario.setDataCadastro(LocalDate.now());
    usuario.setSenha(this.passwordEncoder.encode(usuarioDTO.senha()));
    usuario.setRole(UsuarioRole.ROLE_USUARIO);

    return this.usuarioRepositorio.save(usuario);
  }

  @Transactional
  public void mudarSenha(String nomeUsuario, MudarSenhaDTO mudarSenhaDTO) {
    this.usuarioRepositorio
        .findByNomeUsuario(nomeUsuario)
        .map(
            user -> {
              String senhaAtual = this.passwordEncoder.encode(mudarSenhaDTO.senhaAtual());
              if (user.getSenha().equals(senhaAtual)) {
                user.setSenha(this.passwordEncoder.encode(mudarSenhaDTO.novaSenha()));
                return this.usuarioRepositorio.save(user);
              }
              throw new SenhaIncorretaException("Senha atual incorreta");
            })
        .orElseThrow(() -> new NaoEncontradoException(MSG_ERRO_USUARIO_NAO_ENCONTRADO));
  }

  @Transactional
  public void tornarAdmin(String nomeUsuario) {
    this.usuarioRepositorio
        .findByNomeUsuario(nomeUsuario)
        .map(
            user -> {
              user.setRole(UsuarioRole.ROLE_ADMIN);
              return this.usuarioRepositorio.save(user);
            })
        .orElseThrow(() -> new NaoEncontradoException(MSG_ERRO_USUARIO_NAO_ENCONTRADO));
  }

  @Transactional
  public void deletarUsuario(String nomeUsuario, DeletarContaDTO deletarContaDTO) {
    this.usuarioRepositorio
        .findByNomeUsuario(nomeUsuario)
        .map(
            user -> {
              if (!user.getSenha().equals(this.passwordEncoder.encode(deletarContaDTO.senha()))) {
                throw new SenhaIncorretaException("Senha incorreta");
              }
              this.usuarioRepositorio.deleteByNomeUsuario(nomeUsuario);
              return Void.TYPE;
            })
        .orElseThrow(() -> new NaoEncontradoException(MSG_ERRO_USUARIO_NAO_ENCONTRADO));
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
        .orElseThrow(() -> new NaoEncontradoException(MSG_ERRO_USUARIO_NAO_ENCONTRADO));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.usuarioRepositorio
        .findByNomeUsuario(username)
        .orElseThrow(() -> new UsernameNotFoundException(MSG_ERRO_USUARIO_NAO_ENCONTRADO));
  }

  public Usuario buscarPorNome(String nomeUsuario) {
    return this.usuarioRepositorio
        .findByNomeUsuario(nomeUsuario)
        .orElseThrow(() -> new UsernameNotFoundException(MSG_ERRO_USUARIO_NAO_ENCONTRADO));
  }
}
