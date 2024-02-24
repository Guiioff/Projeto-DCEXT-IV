package br.com.projeto.dtos;

import br.com.projeto.enums.Recursos;
import lombok.Builder;
import org.springframework.hateoas.Link;

import java.time.LocalDate;
import java.util.List;

@Builder
public record LocalRespostaDTO(
    String nome,
    String descricao,
    String rua,
    int numero,
    String bairro,
    List<Recursos> recursos,
    LocalDate dataCadastro,
    String autor,
    Double media,
    List<AvaliacaoRespostaDTO> avaliacoes,
    Link mapa) {}
