package br.com.projeto.controladores;

import br.com.projeto.dtos.AvaliacaoDTO;
import br.com.projeto.servicos.AvaliacaoServico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/avaliacao")
public class AvaliacaoControlador {
  private final AvaliacaoServico avaliacaoServico;

  @PostMapping("/publicar/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  public ModelAndView avaliar(@ModelAttribute @Valid AvaliacaoDTO data, @PathVariable UUID id) {
    this.avaliacaoServico.publicarAvaliacao(id, data);

    ModelAndView mv = new ModelAndView();
    mv.setViewName("home/sucesso");
    return mv;
  }
}
