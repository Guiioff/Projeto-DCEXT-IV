package br.com.projeto.controladores;

import br.com.projeto.dtos.LocalDTO;
import br.com.projeto.dtos.LocalRespostaDTO;
import br.com.projeto.modelos.Local;
import br.com.projeto.servicos.LocalServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    @GetMapping("/ver-local")
    @ResponseStatus(HttpStatus.OK)
    public LocalRespostaDTO visualizarLocal(@RequestParam("nome") String nome){
        return this.localServico.visualizarLocal(nome);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LocalRespostaDTO> exibirLocais(){
        return this.localServico.exibirLocais();
    }

    @GetMapping("/mapa")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView exibirMapa(@RequestParam("nome") String nome){
        return this.localServico.exibirMapa(nome);
    }

}
