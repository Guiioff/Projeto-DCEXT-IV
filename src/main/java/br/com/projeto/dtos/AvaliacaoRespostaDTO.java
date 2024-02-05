package br.com.projeto.dtos;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record AvaliacaoRespostaDTO(
    Float nota, String comentario, LocalDate data, String nomeAutor) {}
