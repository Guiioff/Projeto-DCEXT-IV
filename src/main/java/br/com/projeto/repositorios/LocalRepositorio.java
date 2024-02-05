package br.com.projeto.repositorios;

import br.com.projeto.modelos.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LocalRepositorio extends JpaRepository<Local, UUID> {

  Optional<Local> findByNome(String nome);
}
