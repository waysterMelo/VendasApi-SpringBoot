package main;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import main.domain.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {


        @Value("${security.jwt.expiracao}")
        private String expiracao;

        @Value("${security.jwt.chave-assinatura}")
        private String chaveAssinatura;

        public String gerarToken(Usuario usuario){
                long expString = Long.valueOf(expiracao);
                LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
                Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
                Date data = Date.from(instant);
                return Jwts.builder().setSubject(usuario.getLogin()).setExpiration(data).signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                        .compact();
        }

        private Claims obterClaims(String token) throws ExpiredJwtException {
                return Jwts.parser().setSigningKey(chaveAssinatura).parseClaimsJws(token).getBody();
        }

        public boolean tokenValido(String token){
                try {
                        Claims claims = obterClaims(token);
                        Date data  = claims.getExpiration();
                        LocalDateTime localDateTime = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                        return !LocalDateTime.now().isAfter(localDateTime);
                }catch (ExpiredJwtException e){
                        return false;
                }
        }

        public String obterUsuario(String token) throws ExpiredJwtException{
                return (String) obterClaims(token).getSubject();
        }

        public static void main(String[] args) {
                ConfigurableApplicationContext context = SpringApplication.run(VendasApp.class);
                JwtService jwtService = context.getBean(JwtService.class);
                Usuario usuario = Usuario.builder().login("fulano").build();
                String token = jwtService.gerarToken(usuario);

                boolean is_token_valido = jwtService.tokenValido(token);
                System.out.println("o token esta valido ?" + is_token_valido);
                System.out.println(jwtService.obterUsuario(token));

        }

}
