package br.com.projeto.servicos;

import br.com.projeto.dtos.AvaliacaoDTO;
import br.com.projeto.dtos.AvaliacaoRespostaDTO;
import br.com.projeto.modelos.Avaliacao;
import br.com.projeto.repositorios.AvaliacaoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliacaoServico {
  private final AvaliacaoRepositorio avaliacaoRepositorio;
  private final UsuarioServico usuarioServico;
  private final LocalServico localServico;

  @Transactional
  public void publicarAvaliacao(UUID id, AvaliacaoDTO data) {
    Avaliacao avaliacao =
        Avaliacao.builder()
            .nota(data.nota())
            .comentario(data.comentario())
            .autor(this.usuarioServico.buscarPorNome(data.nomeAutor()))
            .local(this.localServico.buscarPorId(id))
            .data(LocalDate.now())
            .build();

    this.avaliacaoRepositorio.save(avaliacao);
  }

}
