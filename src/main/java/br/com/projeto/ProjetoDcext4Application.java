package br.com.projeto;

import br.com.projeto.enums.UsuarioRole;
import br.com.projeto.modelos.Usuario;
import br.com.projeto.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
public class ProjetoDcext4Application {

  public static void main(String[] args) {
    SpringApplication.run(ProjetoDcext4Application.class, args);
  }

/*
  @Bean
  public CommandLineRunner commandLineRunner(
      @Autowired UsuarioRepositorio repositorio, @Autowired PasswordEncoder passwordEncoder) {
    return args -> {
      Usuario usuario =
          Usuario.builder()
              .nomeUsuario("admin")
              .email("admin@gmail.com")
              .senha(passwordEncoder.encode("admin"))
              .dataNascimento(LocalDate.of(2000, 1, 1))
              .role(UsuarioRole.ROLE_ADMIN)
              .dataCadastro(LocalDate.now())
              .isContaExpirada(false)
              .isAtivo(true)
              .isContaBloqueada(false)
              .isCredenciaisExpiradas(false)
              .build();
      repositorio.save(usuario);
    };
  }
  */
}
