package br.com.projeto.dtos;

import jakarta.validation.constraints.NotBlank;

public record DeletarContaDTO(
    @NotBlank(message = "O campo 'senha' não pode estar em branco") String senha) {}
