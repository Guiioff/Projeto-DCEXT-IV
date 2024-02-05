package br.com.projeto.dtos;

import java.time.LocalDate;

public record UsuarioRespostaDTO(
    String nomeUsuario, String email, LocalDate dataCadastro, LocalDate dataNascimento) {}
