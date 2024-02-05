package br.com.projeto.repositorios;

import br.com.projeto.modelos.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AvaliacaoRepositorio extends JpaRepository<Avaliacao, UUID> {

    @Query("SELECT a FROM Avaliacao a WHERE a.local.nome = :nomeLocal")
    List<Avaliacao> getAllByNomeLocal(@Param("nomeLocal") String nomeLocal);
}
