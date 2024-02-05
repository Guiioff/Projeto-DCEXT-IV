package br.com.projeto.controladores;

import br.com.projeto.dtos.AvaliacaoDTO;
import br.com.projeto.servicos.AvaliacaoServico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/avaliacao")
public class AvaliacaoControlador {
  private final AvaliacaoServico avaliacaoServico;

  @PostMapping("/publicar")
  @ResponseStatus(HttpStatus.CREATED)
  public void avaliar(@RequestBody @Valid AvaliacaoDTO data) {
    this.avaliacaoServico.publicarAvaliacao(data);
  }
}
