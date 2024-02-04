package br.com.projeto.repositorios;

import br.com.projeto.modelos.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalRepositorio extends JpaRepository<Local,Long> {

    Optional<Local> findByNome(String nome);
}
