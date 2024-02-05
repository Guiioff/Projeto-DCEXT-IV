package br.com.projeto.dtos;

import br.com.projeto.enums.Recursos;
import org.springframework.hateoas.Link;

import java.time.LocalDate;
import java.util.List;

public record LocalRespostaDTO(String nome, String descricao, double latitude, double longitude, List<Recursos> recursos, LocalDate dataCadastro, Link mapa) {
}
