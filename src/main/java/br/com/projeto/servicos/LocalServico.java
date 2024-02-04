package br.com.projeto.servicos;

import br.com.projeto.dtos.LocalDTO;
import br.com.projeto.modelos.Local;
import br.com.projeto.repositorios.LocalRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

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
}
