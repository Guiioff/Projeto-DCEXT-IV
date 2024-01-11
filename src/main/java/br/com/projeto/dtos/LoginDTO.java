package br.com.projeto.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "O email do usuário não pode ser nulo ou estar em branco")
        @Email(message = "O email do usuário deve ser válido")
        String email,
        @NotBlank(message = "A senha do usuário não pode ser nula ou estar em branco")
        String senha
) {}
