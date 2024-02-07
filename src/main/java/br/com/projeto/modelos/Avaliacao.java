package br.com.projeto.modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Avaliacao {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id_avaliacao")
  private UUID id;

  @Column(nullable = false)
  private Float nota;

  @Column(nullable = false, length = 10000)
  private String comentario;

  private LocalDate data;

  @ManyToOne
  @JoinColumn(name = "id_autor", referencedColumnName = "id_usuario")
  private Usuario autor;

  @ManyToOne
  @JoinColumn(name = "id_local", referencedColumnName = "id_local")
  private Local local;
}
