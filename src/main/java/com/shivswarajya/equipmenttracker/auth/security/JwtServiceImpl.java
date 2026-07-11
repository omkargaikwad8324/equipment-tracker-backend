package com.shivswarajya.equipmenttracker.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;
    private Key signingKey;

    @PostConstruct
    public void init() {
        signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateToken(String username) {

        System.out.println(">>> JwtServiceImpl.generateToken() called");
        System.out.println(">>> username = " + username);
        System.out.println(">>> secret length = " + secret.length());
        System.out.println(">>> expiration = " + jwtExpiration);

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new java.util.Date())
                .expiration(new java.util.Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(signingKey)
                .compact();

        System.out.println(">>> token = [" + token + "]");

        return token;
    }

    @Override
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    @Override
    public boolean isTokenValid(String token) {

        try {
            Claims claims = extractAllClaims(token);

            return claims.getExpiration().after(new java.util.Date());

        } catch (Exception e) {

            return false;

        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}