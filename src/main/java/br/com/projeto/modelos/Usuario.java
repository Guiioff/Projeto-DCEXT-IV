package br.com.projeto.modelos;

import br.com.projeto.enums.UsuarioRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Usuario implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_usuario")
  private UUID id;

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

  @Enumerated(EnumType.STRING)
  private UsuarioRole role;

  private boolean isContaExpirada;
  private boolean isContaBloqueada;
  private boolean isCredenciaisExpiradas;
  private boolean isAtivo;

  public Usuario() {
    this.isContaExpirada = false;
    this.isContaBloqueada = false;
    this.isCredenciaisExpiradas = false;
    this.isAtivo = true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(this.role.name()));
  }

  @Override
  public String getPassword() {
    return this.senha;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return !this.isContaExpirada;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !this.isContaBloqueada;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return !this.isCredenciaisExpiradas;
  }

  @Override
  public boolean isEnabled() {
    return this.isAtivo;
  }
}
