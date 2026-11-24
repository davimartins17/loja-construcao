package com.exemplo.loja.security;



import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    @Value("${app.jwt.secret}") private String secret;
    @Value("${app.jwt.expiration-ms}") private long expiration;

    private SecretKey key() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    public String gerar(String email) {
        Date agora = new Date();
        return Jwts.builder().subject(email).issuedAt(agora)
                .expiration(new Date(agora.getTime() + expiration))
                .signWith(key()).compact();
    }
    public String extrairEmail(String token) {
        return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token)
                .getPayload().getSubject();
    }
}
