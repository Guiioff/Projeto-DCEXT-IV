package br.com.projeto.dtos;

import org.springframework.hateoas.Link;

import java.time.LocalDate;

public record LocalRespostaDTO(String nome, String descricao, double latitude, double longitude, LocalDate dataCadastro, Link mapa) {
}
