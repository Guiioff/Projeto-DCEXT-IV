package br.com.projeto.dtos;

import java.time.LocalDate;

public record UsuarioRespostaDTO(
    String nome, String email, LocalDate dataCadastro, LocalDate dataNascimento) {}
