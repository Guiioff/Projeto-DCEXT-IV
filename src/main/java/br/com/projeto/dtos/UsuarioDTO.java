package br.com.projeto.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UsuarioDTO(
    @NotBlank(message = "O nome de usuário não pode ser nulo ou estar em branco")
        String nomeUsuario,
    @NotBlank(message = "O email do usuário não pode ser nulo ou estar em branco")
        @Email(message = "O email do usuário deve ser válido")
        String email,
    @NotBlank(message = "A senha do usuário não pode ser nula ou estar em branco") String senha,
    @NotNull(message = "A data de nascimento do usuário não pode ser nula")
        LocalDate dataNascimento) {}
