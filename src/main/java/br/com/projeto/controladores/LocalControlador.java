package br.com.projeto.controladores;

import br.com.projeto.dtos.LocalDTO;
import br.com.projeto.dtos.LocalRespostaDTO;
import br.com.projeto.modelos.Local;
import br.com.projeto.servicos.JwtServico;
import br.com.projeto.servicos.LocalServico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@RestController
@RequestMapping("/local")
@RequiredArgsConstructor
public class LocalControlador {

  private final LocalServico localServico;
  private final JwtServico jwtServico;

  @PostMapping("/cadastrar")
  @ResponseStatus(HttpStatus.CREATED)
  public UUID cadastrarLocal(
      @RequestHeader("Authorization") String token, @RequestBody @Valid LocalDTO dto) {
    String nome = this.jwtServico.redirecionarUsername(token);
    return this.localServico.cadastrarLocal(nome, dto);
  }

  @GetMapping("/ver-local")
  @ResponseStatus(HttpStatus.OK)
  public LocalRespostaDTO visualizarLocal(@RequestParam("nome") String nome) {
    return this.localServico.visualizarLocal(nome);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<LocalRespostaDTO> exibirLocais(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "nome") String order) {

    return this.localServico.exibirLocais(page, size, order);
  }

  @GetMapping("/mapa")
  @ResponseStatus(HttpStatus.OK)
  public ModelAndView exibirMapa(@RequestParam("nome") String nome) {
    return this.localServico.exibirMapa(nome);
  }
}
