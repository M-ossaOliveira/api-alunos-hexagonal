package com.example.api_aluno.adapters.out.security;

import com.example.api_aluno.domain.usuario.Perfil;
import com.example.api_aluno.domain.usuario.Usuario;
import com.example.api_aluno.ports.out.TokenProviderPort;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProviderAdapter implements TokenProviderPort {
    private final String secret;
    private final long expirationSeconds;
    public JwtTokenProviderAdapter(
            @Value("${security.jwt.secret:change-me-please-change-me-change-me}") String secret,
            @Value("${security.jwt.expiration:3600}") long expirationSeconds) {
        this.secret = secret;
        this.expirationSeconds = expirationSeconds;
    }
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    @Override
    public String generateToken(Usuario usuario) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + (expirationSeconds * 1000));
        String roles = usuario.getPerfis() == null ? "" : usuario.getPerfis().stream()
                .map(Perfil::name)
                .collect(Collectors.joining(","));
        return Jwts.builder()
                .setSubject(usuario.getUsername())
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    @Override
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
