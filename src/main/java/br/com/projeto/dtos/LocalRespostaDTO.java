package br.com.projeto.dtos;

import java.time.LocalDate;

public record LocalRespostaDTO(String nome, String descricao, double latitude, double longitude, LocalDate dataCadastro) {
}
