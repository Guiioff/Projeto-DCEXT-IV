package br.com.projeto.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeletarContaDTO(
    @NotBlank(message = "O campo 'senha' não pode estar em branco")
        @NotNull(message = "O campo 'senha' não pode ser nulo")
        String senha) {}
