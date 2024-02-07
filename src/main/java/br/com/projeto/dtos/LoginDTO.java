package br.com.projeto.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
    @NotBlank(message = "O nome de usuário não pode ser nulo ou estar em branco")
        String nomeUsuario,
    @NotBlank(message = "A senha do usuário não pode ser nula ou estar em branco") String senha) {}
