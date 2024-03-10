package br.com.projeto.config;

import br.com.projeto.servicos.UsuarioServico;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class ConfiguracaoSeguranca {
  private final FiltroAutenticacaoJwt filtroAutenticacaoJwt;
  private final UsuarioServico usuarioServico;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationEntryPoint authenticationEntryPoint;

  public ConfiguracaoSeguranca(
      FiltroAutenticacaoJwt filtroJwt,
      UsuarioServico servico,
      PasswordEncoder encoder,
      @Qualifier("customAuthenticationEntryPoint") AuthenticationEntryPoint entryPoint) {
    this.filtroAutenticacaoJwt = filtroJwt;
    this.usuarioServico = servico;
    this.passwordEncoder = encoder;
    this.authenticationEntryPoint = entryPoint;
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provedor = new DaoAuthenticationProvider();
    provedor.setUserDetailsService(this.usuarioServico);
    provedor.setPasswordEncoder(this.passwordEncoder);
    return provedor;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(
            exception -> exception.authenticationEntryPoint(this.authenticationEntryPoint))
        .authorizeHttpRequests(
            request ->
                request
                    .requestMatchers(HttpMethod.POST, "/auth/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/local/**")
                    .permitAll()
                        .requestMatchers(HttpMethod.POST, "/avaliacao/**")
                        .permitAll()
                    .anyRequest()
                    .authenticated())
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(this.filtroAutenticacaoJwt, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
