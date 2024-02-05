package br.com.projeto.controladores;

import br.com.projeto.dtos.DeletarContaDTO;
import br.com.projeto.dtos.MudarSenhaDTO;
import br.com.projeto.dtos.UsuarioRespostaDTO;
import br.com.projeto.servicos.JwtServico;
import br.com.projeto.servicos.UsuarioServico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioControlador {
  private final JwtServico jwtServico;
  private final UsuarioServico usuarioServico;

  @GetMapping("/perfil")
  @ResponseStatus(HttpStatus.OK)
  public UsuarioRespostaDTO verPerfil(@RequestHeader("Authorization") String token) {
    String nomeUsuario = this.jwtServico.redirecionarUsername(token);
    return this.usuarioServico.consultarUsuario(nomeUsuario);
  }

  @GetMapping("/consultar-usuario")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public UsuarioRespostaDTO consultarUsuario(@RequestParam(value = "nome") String nome) {
    return this.usuarioServico.consultarUsuario(nome);
  }

  @PatchMapping("/mudar-senha")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void mudarSenha(
      @RequestHeader("Authorization") String token, @RequestBody @Valid MudarSenhaDTO dto) {
    String nomeUsuario = this.jwtServico.redirecionarUsername(token);
    this.usuarioServico.mudarSenha(nomeUsuario, dto);
  }

  @PatchMapping("/mudar-role")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void tornarAdmin(@RequestParam(value = "nome") String nome) {
    this.usuarioServico.tornarAdmin(nome);
  }

  @PatchMapping("/mudar-bloqueio")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void mudarBloqueio(@RequestParam(value = "nome") String nome) {
    this.usuarioServico.mudarBloqueioConta(nome);
  }

  @DeleteMapping("/deletar-conta")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletarConta(
      @RequestHeader("Authorization") String token, @RequestBody @Valid DeletarContaDTO dto) {
    String nome = this.jwtServico.redirecionarUsername(token);
    this.usuarioServico.deletarUsuario(nome, dto);
  }
}
