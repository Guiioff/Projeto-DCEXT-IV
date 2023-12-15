package br.com.projeto.dtos;

import java.util.Date;

public record UsuarioDTO(String nome, String email, String senha, Date dataNascimento) {}
