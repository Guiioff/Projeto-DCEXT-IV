package br.com.projeto.controladores;

import br.com.projeto.dtos.LoginDTO;
import br.com.projeto.dtos.TokenDTO;
import br.com.projeto.dtos.UsuarioDTO;
import br.com.projeto.servicos.AutenticacaoServico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AutenticacaoControlador {
  private final AutenticacaoServico autenticacaoServico;

  @PostMapping("/cadastro")
  public ResponseEntity<TokenDTO> cadastrar(@RequestBody @Valid UsuarioDTO usuarioDTO) {
    TokenDTO token = this.autenticacaoServico.cadastrarUsuario(usuarioDTO);
    return ResponseEntity.status(HttpStatus.OK).body(token);
  }

  @PostMapping("/login")
  public ResponseEntity<TokenDTO> logar(@RequestBody @Valid LoginDTO loginDTO) {
    TokenDTO token = this.autenticacaoServico.logarUsuario(loginDTO);
    return ResponseEntity.status(HttpStatus.OK).body(token);
  }
}
