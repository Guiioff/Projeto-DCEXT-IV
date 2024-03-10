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
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/local")
@RequiredArgsConstructor
public class LocalControlador {

  private final LocalServico localServico;
  private final JwtServico jwtServico;

  @PostMapping(path = "/cadastrar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ModelAndView cadastrarLocalForm(@ModelAttribute @Valid LocalDTO dto) {
     this.localServico.cadastrarLocal(dto);

    ModelAndView mv = new ModelAndView();
    mv.setViewName("home/sucesso");
    return mv;
  }

  @GetMapping("/ver-local")
  @ResponseStatus(HttpStatus.OK)
  public ModelAndView visualizarLocal(@RequestParam("nome") String nome) {
    return this.localServico.visualizarLocal(nome);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public ModelAndView exibirLocais() {
    return this.localServico.exibirLocais();
  }

  @GetMapping("/mapa")
  @ResponseStatus(HttpStatus.OK)
  public ModelAndView exibirMapa(@RequestParam("nome") String nome) {
    return this.localServico.exibirMapa(nome);
  }
}
