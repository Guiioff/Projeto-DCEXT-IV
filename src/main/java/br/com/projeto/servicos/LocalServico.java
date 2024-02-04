package br.com.projeto.servicos;

import br.com.projeto.dtos.LocalDTO;
import br.com.projeto.dtos.LocalRespostaDTO;
import br.com.projeto.modelos.Local;
import br.com.projeto.repositorios.LocalRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

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

        return new LocalRespostaDTO(local.getNome(),
                local.getDescricao(),
                local.getLatitude(),
                local.getLongitude(),
                local.getDataCadastro());
    }
}
