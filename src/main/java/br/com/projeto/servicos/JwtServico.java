package br.com.projeto.servicos;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServico {

  @Value("${jwt.secret.key}")
  private String jwtSecretKey;

  @Value("${jwt.tempo.expiracao}")
  private Long jwtTempoExpiracao;

  public String gerarToken(UserDetails usuario) {
    return doGerarToken(new HashMap<>(), usuario);
  }

  public boolean isTokenValido(String token, UserDetails usuario) {
    String username = extrairUsername(token);
    return username.equals(usuario.getUsername()) && !isTokenExpirado(token);
  }

  public String extrairUsername(String token) {
    return extrairClaim(token, Claims::getSubject);
  }

  private String doGerarToken(Map<String, Object> claims, UserDetails usuario) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(usuario.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + this.jwtTempoExpiracao))
        .signWith(getChaveAssinatura(), SignatureAlgorithm.HS256)
        .compact();
  }

  private boolean isTokenExpirado(String token) {
    return extrairExpiracao(token).before(new Date(System.currentTimeMillis()));
  }

  private Date extrairExpiracao(String token) {
    return extrairClaim(token, Claims::getExpiration);
  }

  private <T> T extrairClaim(String token, Function<Claims, T> funcaoClaim) {
    Claims claims = extrairTodasClaims(token);
    return funcaoClaim.apply(claims);
  }

  private Claims extrairTodasClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getChaveAssinatura())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getChaveAssinatura() {
    byte[] chaveBytes = Decoders.BASE64.decode(this.jwtSecretKey);
    return Keys.hmacShaKeyFor(chaveBytes);
  }

  public String redirecionarUsername(String header) {
    String token = header.substring(7);
    return extrairUsername(token);
  }
}
