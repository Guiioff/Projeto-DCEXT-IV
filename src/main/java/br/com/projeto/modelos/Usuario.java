package br.com.projeto.modelos;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_usuario")
  private Long id;

  @Column(nullable = false, length = 150)
  private String nome;

  @Column(nullable = false, length = 100, unique = true)
  private String email;

  @Column(nullable = false, length = 100)
  private String senha;

  @Temporal(TemporalType.TIMESTAMP)
  private Date dataCadastro;

  @Column(nullable = false)
  private Date dataNascimento;
}
