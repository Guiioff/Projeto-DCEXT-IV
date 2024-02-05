package br.com.projeto.servicos;

import br.com.projeto.controladores.LocalControlador;
import br.com.projeto.dtos.AvaliacaoRespostaDTO;
import br.com.projeto.dtos.LocalDTO;
import br.com.projeto.dtos.LocalRespostaDTO;
import br.com.projeto.excecoes.NaoEncontradoException;
import br.com.projeto.modelos.Avaliacao;
import br.com.projeto.modelos.Local;
import br.com.projeto.repositorios.AvaliacaoRepositorio;
import br.com.projeto.repositorios.LocalRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocalServico {
  private final LocalRepositorio localRepositorio;
  private final UsuarioServico usuarioServico;
  private final AvaliacaoRepositorio avaliacaoRepositorio;

  @Transactional
  public UUID cadastrarLocal(String nomeUsuario, LocalDTO dto) {

    Local novoLocal = new Local();
    BeanUtils.copyProperties(dto, novoLocal);
    novoLocal.setDataCadastro(LocalDate.now());
    novoLocal.setUsuarioDono(this.usuarioServico.buscarPorNome(nomeUsuario));

    return this.localRepositorio.save(novoLocal).getId();
  }

  public LocalRespostaDTO visualizarLocal(String nome) {
    return this.localRepositorio
        .findByNome(nome)
        .map(
            local -> {
              Link linkMapa =
                  WebMvcLinkBuilder.linkTo(
                          WebMvcLinkBuilder.methodOn(LocalControlador.class)
                              .exibirMapa(local.getNome()))
                      .withRel("Mapa");

              return new LocalRespostaDTO(
                  local.getNome(),
                  local.getDescricao(),
                  local.getLatitude(),
                  local.getLongitude(),
                  local.getRecursos(),
                  local.getDataCadastro(),
                  local.getUsuarioDono().getNomeUsuario(),
                  gerarListaAvaliacoes(local.getNome()),
                  linkMapa);
            })
        .orElseThrow(() -> new NaoEncontradoException("Local não encontrado"));
  }

  public Page<LocalRespostaDTO> exibirLocais(int page, int size, String order) {

    Pageable pageable = PageRequest.of(page, size, Sort.by(order));
    Page<Local> locaisPaginados = this.localRepositorio.findAll(pageable);

    return locaisPaginados.map(
        local ->
            new LocalRespostaDTO(
                local.getNome(),
                local.getDescricao(),
                local.getLatitude(),
                local.getLongitude(),
                local.getRecursos(),
                local.getDataCadastro(),
                local.getUsuarioDono().getNomeUsuario(),
                gerarListaAvaliacoes(local.getNome()),
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(LocalControlador.class)
                            .exibirMapa(local.getNome()))
                    .withRel("Mapa")));
  }

  public ModelAndView exibirMapa(String nome) {
    Optional<Local> localBanco = this.localRepositorio.findByNome(nome);

    if (localBanco.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Local não encontrado.");
    }

    Local local = localBanco.get();

    ModelAndView mv = new ModelAndView();
    mv.setViewName("home/index");
    mv.addObject("latitude", local.getLatitude());
    mv.addObject("longitude", local.getLongitude());
    return mv;
  }

  public Local buscarPorId(UUID id) {
    return this.localRepositorio
        .findById(id)
        .orElseThrow(() -> new NaoEncontradoException("Local não encontrado"));
  }

  public AvaliacaoRespostaDTO converterParaResposta(Avaliacao avaliacao) {
    return AvaliacaoRespostaDTO.builder()
        .nota(avaliacao.getNota())
        .comentario(avaliacao.getComentario())
        .nomeAutor(avaliacao.getAutor().getNomeUsuario())
        .data(avaliacao.getData())
        .build();
  }

  public List<AvaliacaoRespostaDTO> gerarListaAvaliacoes(String nomeLocal) {
    List<Avaliacao> avaliacoes = this.avaliacaoRepositorio.getAllByNomeLocal(nomeLocal);

    return avaliacoes.stream().map(this::converterParaResposta).toList();
  }
}
