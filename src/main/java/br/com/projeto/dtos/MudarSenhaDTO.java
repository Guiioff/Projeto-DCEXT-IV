package br.com.projeto.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MudarSenhaDTO(
    @NotBlank(message = "O campo 'senha atual' não pode estar em branco")
        @NotNull(message = "O campo 'senha atual' não pode ser nulo")
        String senhaAtual,
    @NotBlank(message = "O campo 'nova senha' não pode estar em branco")
        @NotNull(message = "O campo 'senha atual' não pode ser nulo")
        String novaSenha) {}
