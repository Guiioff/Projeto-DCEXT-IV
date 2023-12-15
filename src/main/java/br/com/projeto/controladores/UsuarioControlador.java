package br.com.projeto.controladores;

import br.com.projeto.dtos.UsuarioDTO;
import br.com.projeto.servicos.UsuarioServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

  @Autowired private UsuarioServico usuarioServico;

  @PostMapping("/cadastro")
  public ResponseEntity<String> cadastrarUsuário(@RequestBody UsuarioDTO usuarioDTO) {
    this.usuarioServico.cadastrarUsuario(usuarioDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso");
  }
}
