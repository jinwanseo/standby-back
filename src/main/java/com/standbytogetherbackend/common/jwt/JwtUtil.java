package com.standbytogetherbackend.common.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private Long expire;
    private SecretKey secretKey;

    public JwtUtil(@Value("${spring.jwt.secret}") String secret,
        @Value("${spring.jwt.expire}") Long expire) {

        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
            SIG.HS256.key().build()
                .getAlgorithm());
        this.expire = expire;
    }

    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
            .get("username", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
            .get("role", String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
            .getExpiration().before(new Date());
    }

    public Long getId(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
            .get("id", Long.class);
    }

    public String createToken(Long id, String username, String role) {
        return Jwts.builder()
            .claim("id", id)
            .claim("username", username)
            .claim("role", role)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expire))
            .signWith(secretKey)
            .compact();
    }
}
