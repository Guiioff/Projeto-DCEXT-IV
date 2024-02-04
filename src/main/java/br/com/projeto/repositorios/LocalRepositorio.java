package br.com.projeto.repositorios;

import br.com.projeto.modelos.Local;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalRepositorio extends JpaRepository<Local,Long> {
}
