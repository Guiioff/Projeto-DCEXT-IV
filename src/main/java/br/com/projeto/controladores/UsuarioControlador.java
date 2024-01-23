package br.com.projeto.controladores;

import br.com.projeto.dtos.DeletarContaDTO;
import br.com.projeto.dtos.MudarSenhaDTO;
import br.com.projeto.dtos.UsuarioRespostaDTO;
import br.com.projeto.servicos.JwtServico;
import br.com.projeto.servicos.UsuarioServico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioControlador {
  private final JwtServico jwtServico;
  private final UsuarioServico usuarioServico;

  @GetMapping("/perfil")
  public ResponseEntity<UsuarioRespostaDTO> verPerfil(
      @RequestHeader("Authorization") String token) {
    String email = this.jwtServico.extrairUsername(token);
    UsuarioRespostaDTO resposta = this.usuarioServico.consultarUsuario(email);
    return ResponseEntity.ok(resposta);
  }

  @GetMapping("/consultar-usuario")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<UsuarioRespostaDTO> consultarUsuario(
      @RequestParam(value = "email") String email) {
    UsuarioRespostaDTO resposta = this.usuarioServico.consultarUsuario(email);
    return ResponseEntity.ok(resposta);
  }

  @PutMapping("/mudar-senha")
  public ResponseEntity<String> mudarSenha(
      @RequestHeader("Authorization") String token, @RequestBody @Valid MudarSenhaDTO dto) {
    String email = this.jwtServico.extrairUsername(token);
    this.usuarioServico.mudarSenha(email, dto);
    return ResponseEntity.ok("Senha alterada com sucesso");
  }

  @PutMapping("/mudar-role")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<String> tornarAdmin(@RequestParam(value = "email") String email) {
    this.usuarioServico.tornarAdmin(email);
    return ResponseEntity.ok("Alteração realizada com sucesso");
  }

  @PutMapping("/mudar-bloqueio")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<String> mudarBloqueio(@RequestParam(value = "email") String email) {
    this.usuarioServico.mudarBloqueioConta(email);
    return ResponseEntity.ok("Alteração realizada com sucesso");
  }

  @DeleteMapping("/deletar-conta")
  public ResponseEntity<String> deletarConta(
      @RequestHeader("Authorization") String token, @RequestBody @Valid DeletarContaDTO dto) {
    String email = this.jwtServico.extrairUsername(token);
    this.usuarioServico.deletarUsuario(email, dto);
    return ResponseEntity.ok("Usuário deletado com sucesso");
  }
}
