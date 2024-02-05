package br.com.projeto.dtos;

import br.com.projeto.enums.Recursos;

import java.util.List;

public record LocalDTO(String nome, String descricao, double latitude, double longitude, List<Recursos> recursos) {
}
