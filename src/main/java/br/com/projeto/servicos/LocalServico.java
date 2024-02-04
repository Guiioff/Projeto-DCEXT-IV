package br.com.projeto.servicos;

import br.com.projeto.controladores.LocalControlador;
import br.com.projeto.dtos.LocalDTO;
import br.com.projeto.dtos.LocalRespostaDTO;
import br.com.projeto.modelos.Local;
import br.com.projeto.repositorios.LocalRepositorio;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocalServico {

    @Autowired
    private LocalRepositorio localRepositorio;

    @Transactional
    public Local cadastrarLocal(LocalDTO dto){

        Local novoLocal = new Local();
        BeanUtils.copyProperties(dto, novoLocal);
        novoLocal.setDataCadastro(LocalDate.now());
        novoLocal.setUsuarioDono(null);

        return this.localRepositorio.save(novoLocal);
    }

    public LocalRespostaDTO visualizarLocal(String nome){
        Optional<Local> localBanco = this.localRepositorio.findByNome(nome);

        if(localBanco.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Local não encontrado.");
        }

        Local local = localBanco.get();

        Link linkMapa = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(LocalControlador.class).exibirMapa(local.getNome()))
                .withRel("Mapa");

        return new LocalRespostaDTO(local.getNome(),
                local.getDescricao(),
                local.getLatitude(),
                local.getLongitude(),
                local.getDataCadastro(),
                linkMapa);
    }

    public List<LocalRespostaDTO> exibirLocais(){
        List<Local> locais = this.localRepositorio.findAll();

        if(CollectionUtils.isEmpty(locais)){
            return Collections.emptyList();
        }

        return locais.stream()
                .map(local -> new LocalRespostaDTO(local.getNome(),
                        local.getDescricao(),
                        local.getLatitude(),
                        local.getLongitude(),
                        local.getDataCadastro(),
                        WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder.methodOn(LocalControlador.class).exibirMapa(local.getNome()))
                                .withRel("Mapa"))
                ).collect(Collectors.toList());
    }

    public ModelAndView exibirMapa(String nome){
        Optional<Local> localBanco = this.localRepositorio.findByNome(nome);

        if(localBanco.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Local não encontrado.");
        }

        Local local = localBanco.get();

        ModelAndView mv = new ModelAndView();
        mv.setViewName("home/index");
        mv.addObject("latitude", local.getLatitude());
        mv.addObject("longitude", local.getLongitude());
        return mv;
    }
}
