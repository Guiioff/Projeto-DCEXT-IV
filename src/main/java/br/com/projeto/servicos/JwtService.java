package br.com.projeto.servicos;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

  @Value("${jwt.secret.key}")
  private String jwtSecretKey;

  @Value("${jwt.tempo.expiracao}")
  private Long jwtTempoExpiracao;

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
}
