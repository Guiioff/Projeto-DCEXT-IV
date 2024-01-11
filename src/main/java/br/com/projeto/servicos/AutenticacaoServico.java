package br.com.projeto.servicos;

import br.com.projeto.dtos.LoginDTO;
import br.com.projeto.dtos.TokenDTO;
import br.com.projeto.dtos.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacaoServico {
  private final UsuarioServico usuarioServico;
  private final JwtServico jwtServico;
  private final AuthenticationManager authenticationManager;

  public TokenDTO cadastrarUsuario(UsuarioDTO usuarioDTO) {
    UserDetails usuarioSalvo = this.usuarioServico.cadastrarUsuario(usuarioDTO);
    String token = this.jwtServico.gerarToken(usuarioSalvo);
    return new TokenDTO(token);
  }

  public TokenDTO logarUsuario(LoginDTO loginDTO) {
    this.authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.senha()));

    UserDetails usuario = this.usuarioServico.loadUserByUsername(loginDTO.email());
    String token = this.jwtServico.gerarToken(usuario);
    return new TokenDTO(token);
  }
}
