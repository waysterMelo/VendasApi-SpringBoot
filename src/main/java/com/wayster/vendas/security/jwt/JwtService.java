package com.wayster.vendas.security.jwt;

import com.wayster.vendas.Entity.User;
import com.wayster.vendas.VendasApplication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

    @Value("${security.jwt.chave_assinatura}")
    private String chaveAssinatura;

    public String gerarToken(User usuario){
        long expString = Long.parseLong(expiracao);
        LocalDateTime dataHoraExpiracao =  LocalDateTime.now().plusMinutes(expString);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        return Jwts.builder().setSubject(usuario.getLogin()).setExpiration(data).signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                .compact();
    }

    //obter as informaçoes do token
    public Claims obterClaims(String token) throws ExpiredJwtException{
        return Jwts.parser().setSigningKey(chaveAssinatura).parseClaimsJws(token).getBody();
    }

    public boolean tokenValido(String token){
        try {
            Claims claims = obterClaims(token);
            Date expiracao = claims.getExpiration();
            LocalDateTime localDateTime = expiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            //token é valido qd a data atual nao e posterior a da expiracao
            return !LocalDateTime.now().isAfter(localDateTime);
        }catch (Exception e){
            return false;
        }
    }

    public String obterLoginUser(String token) throws ExpiredJwtException{
            return (String) obterClaims(token).getSubject();

    }

    public static void main(String[] args) {
        //rodar a classe da app com context
        ConfigurableApplicationContext context = SpringApplication.run(VendasApplication.class);

        //pegar o bean e armazena em jwt service
        JwtService jwtService = context.getBean(JwtService.class);

        //construir usuario com login
        User usuario = User.builder().login("user").build();

        //chama o metodo gerarToken e armazena em uma string
        String token = jwtService.gerarToken(usuario);

        System.out.println(token);

        boolean isValid = jwtService.tokenValido(token);
        System.out.println("Token valido ? " + isValid);

        System.out.println(jwtService.obterLoginUser(token));
    }

}
