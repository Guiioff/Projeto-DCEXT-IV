package br.com.projeto.dtos;

import br.com.projeto.enums.Recursos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record   LocalDTO(
    @NotBlank(message = "O nome do local não pode ser nulo ou estar em branco") String nome,
    @NotBlank(message = "A descrição do local não pode ser nula ou estar em branco")
        String descricao,
    @NotBlank(message = "A rua do local não pode ser nula ou estar em branco")
    String rua,
    @NotNull(message = "O número do local não pode ser nula ou estar em branco")
    int numero,
    @NotBlank(message = "O bairro do local não pode ser nula ou estar em branco")
    String bairro,
    List<Recursos> recursos) {}
