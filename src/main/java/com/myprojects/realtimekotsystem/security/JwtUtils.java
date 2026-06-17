package com.myprojects.realtimekotsystem.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    // Must be at least 256 bits long for HMAC-SHA algorithms. Keep this in environment variables in prod!
    private final String SECRET_KEY = "52eeeedcd45a345462d02d3daaccca61ddc564b8b9b0f4d6a73b0723ad57aa3e";
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 Hours validity

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Generate Token packed with Multi-Tenant Claims
    public String generateToken(String email, String role, Long restaurantId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("restaurantId", restaurantId); // Vital multi-tenancy identifier

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract Username (Email) from Token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract Specific Claims (like role or restaurantId)
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Validate if Token belongs to user and isn't expired
    public boolean isTokenValid(String token, String email) {
        final String extractedEmail = extractEmail(token);
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
