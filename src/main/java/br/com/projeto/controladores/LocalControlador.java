package br.com.projeto.controladores;

import br.com.projeto.dtos.LocalDTO;
import br.com.projeto.modelos.Local;
import br.com.projeto.servicos.LocalServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/local")
public class LocalControlador {

    @Autowired
    private LocalServico localServico;

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public Local cadastrarLocal(@RequestBody LocalDTO dto){
        return this.localServico.cadastrarLocal(dto);
    }
}
