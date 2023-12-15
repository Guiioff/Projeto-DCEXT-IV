package br.com.projeto.servicos;

import br.com.projeto.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServico {

  @Autowired private UsuarioRepositorio usuarioRepositorio;
}
