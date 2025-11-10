package com.example.login.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {

    // ⚠️ En prod: définis JWT_SECRET dans l'environnement (32+ bytes)
    private final String jwtSecret = System.getenv("JWT_SECRET") != null
            ? System.getenv("JWT_SECRET")
            : "dev-only-please-change-this-32-bytes-min-secret!!";

    private final long jwtExpirationMs = 86400000L; // 1 jour

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /* =========================================================
       Génération du token avec rôle actif (obligatoire)
       ========================================================= */
    public String generateToken(String username, String activeRole) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .claim("activeRole", activeRole)       // <- rôle actif
                .signWith(getSigningKey())
                .compact();
    }

    // (Optionnel) variante si tu veux aussi embarquer la liste des rôles possibles
    public String generateToken(String username, String activeRole, List<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .claim("activeRole", activeRole)
                .claim("roles", roles)
                .signWith(getSigningKey())
                .compact();
    }

    /* =========================
       Getters depuis le token
       ========================= */
    public String getUsernameFromToken(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String getActiveRoleFromToken(String token) {
        return extractAllClaims(token).get("activeRole", String.class);
    }

    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        Object roles = extractAllClaims(token).get("roles");
        return (roles instanceof List) ? (List<String>) roles : List.of();
    }

    /* =========================
       Validation & expiration
       ========================= */
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    // .setAllowedClockSkewSeconds(60) // (optionnel) tolérance d’horloge
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("JWT validation error: " + e.getMessage());
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            return extractAllClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /* =========================
       Utilitaire interne
       ========================= */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
