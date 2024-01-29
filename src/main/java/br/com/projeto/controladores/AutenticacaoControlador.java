package br.com.projeto.controladores;

import br.com.projeto.dtos.LoginDTO;
import br.com.projeto.dtos.TokenDTO;
import br.com.projeto.dtos.UsuarioDTO;
import br.com.projeto.servicos.AutenticacaoServico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AutenticacaoControlador {
  private final AutenticacaoServico autenticacaoServico;

  @PostMapping("/cadastro")
  @ResponseStatus(HttpStatus.CREATED)
  public TokenDTO cadastrar(@RequestBody @Valid UsuarioDTO usuarioDTO) {
    return this.autenticacaoServico.cadastrarUsuario(usuarioDTO);
  }

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public TokenDTO logar(@RequestBody @Valid LoginDTO loginDTO) {
    return this.autenticacaoServico.logarUsuario(loginDTO);
  }
}
