package br.com.projeto.repositorios;

import br.com.projeto.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, UUID> {
  Optional<Usuario> findByEmail(String email);

  boolean existsByEmail(String email);
}
