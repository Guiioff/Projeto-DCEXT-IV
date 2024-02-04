package br.com.projeto.modelos;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Local {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_local")
	private UUID id;
	
	@Column(nullable = false, length = 200)
	private String nome;

	@Column(nullable = false)
	private String descricao;
	
	@Column(nullable = false)
	private double latitude;
	
	@Column(nullable = false)
	private double longitude;

	@Column(nullable = false)
	private LocalDate dataCadastro;

	@ManyToOne
	@JoinColumn(name = "usuarioDono_id")
	private Usuario usuarioDono;
}
