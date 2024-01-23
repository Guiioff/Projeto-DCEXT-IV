package br.com.projeto.dtos;

import java.util.Date;

public record UsuarioRespostaDTO(
    String nome, String email, Date dataCadastro, Date dataNascimento) {}
