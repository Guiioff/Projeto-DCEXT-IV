package br.com.projeto.servicos;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

  @Value("${jwt.secret.key}")
  private String jwtSecretKey;

  @Value("${jwt.tempo.expiracao}")
  private Long jwtTempoExpiracao;


}
