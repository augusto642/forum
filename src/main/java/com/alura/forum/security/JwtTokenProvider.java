package com.alura.forum.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.alura.forum.config.JwtConfig;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtConfig jwtConfig;

    private PrivateKey privateKey;  // Chave privada
    private PublicKey publicKey;    // Chave pública

    // Método para gerar o token usando RS256
    @SuppressWarnings("deprecation")
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getExpiration());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.RS256, privateKey)  // Usando chave privada
                .compact();
    }

    // Método para recuperar o nome de usuário a partir do token
    public String getUsernameFromToken(String token) {
        @SuppressWarnings("deprecation")
        Claims claims = Jwts.parser()
                .setSigningKey(publicKey)  // Usando chave pública para validar
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // Método para validar o token
    @SuppressWarnings("deprecation")
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
