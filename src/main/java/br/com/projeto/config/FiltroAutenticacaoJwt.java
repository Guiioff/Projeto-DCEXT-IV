package br.com.projeto.config;

import br.com.projeto.servicos.JwtServico;
import br.com.projeto.servicos.UsuarioServico;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FiltroAutenticacaoJwt extends OncePerRequestFilter {
  private final JwtServico jwtServico;
  private final UsuarioServico usuarioServico;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String cabecalhoAutorizacao = request.getHeader("Authorization");

    if (cabecalhoAutorizacao == null || !cabecalhoAutorizacao.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String jwtToken = cabecalhoAutorizacao.substring(7);
    String username = this.jwtServico.extrairUsername(jwtToken);

    if (!username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails usuario = this.usuarioServico.loadUserByUsername(username);
      if (this.jwtServico.isTokenValido(jwtToken, usuario)) {
        SecurityContext contextoSeguranca = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken tokenAutenticacao =
            new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        tokenAutenticacao.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        contextoSeguranca.setAuthentication(tokenAutenticacao);
        SecurityContextHolder.setContext(contextoSeguranca);
      }
    }

    filterChain.doFilter(request, response);
  }
}
