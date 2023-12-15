package br.com.projeto.controladores;

import br.com.projeto.servicos.UsuarioServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UsuarioControlador {

  @Autowired private UsuarioServico usuarioServico;
}
