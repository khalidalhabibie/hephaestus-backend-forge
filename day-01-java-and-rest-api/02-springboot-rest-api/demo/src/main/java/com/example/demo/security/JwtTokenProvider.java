package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.model.User;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long expiration;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    // ✅ GENERATE TOKEN (pakai username sebagai subject)
    public String generate(User user) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(user.getUsername()) // ✅ FIX: pakai username
                .claim("role", user.getRole().name())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ AMBIL USERNAME DARI TOKEN
    public String getUsername(String token) {
        return claims(token).getSubject();
    }

    // ✅ VALIDASI TOKEN
    public boolean valid(String token) {
        try {
            claims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ AMBIL CLAIMS
    private Claims claims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}