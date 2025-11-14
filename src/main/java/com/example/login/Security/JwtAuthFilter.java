package com.example.login.Security;

import com.example.login.Models.Utilisateur;
import com.example.login.Services.UtilisateurService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UtilisateurService utilisateurService;

    // ✅ Utilisation de @Lazy pour éviter la boucle entre SecurityConfig ↔ UtilisateurServiceImpl
    public JwtAuthFilter(JwtUtil jwtUtil, @Lazy UtilisateurService utilisateurService) {
        this.jwtUtil = jwtUtil;
        this.utilisateurService = utilisateurService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();
        System.out.println("=== JWT FILTER DEBUG ===");
        System.out.println("Request path: " + path);

        if (shouldSkipAuthentication(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            try {
                username = jwtUtil.getUsernameFromToken(token);
            } catch (ExpiredJwtException eje) {
                writeUnauthorized(response, "TOKEN_EXPIRED");
                return;
            } catch (Exception e) {
                writeUnauthorized(response, "TOKEN_INVALID");
                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // 🔹 Charger l’utilisateur complet depuis la base
                Utilisateur utilisateur = utilisateurService.getByUsername(username);
                if (utilisateur == null) {
                    writeUnauthorized(response, "USER_NOT_FOUND");
                    return;
                }

                // 🔹 Vérifier la validité du token
                if (!jwtUtil.validateJwtToken(token)) {
                    writeUnauthorized(response, "TOKEN_INVALID");
                    return;
                }

                // 🔹 Rôle actif depuis le token
                String activeRole = jwtUtil.getActiveRoleFromToken(token);
                if (activeRole == null || activeRole.isBlank()) {
                    writeUnauthorized(response, "ACTIVE_ROLE_MISSING");
                    return;
                }

                var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + activeRole));

                // ✅ Authentifier avec l’entité Utilisateur complète
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(utilisateur, null, authorities);

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            } catch (ExpiredJwtException eje) {
                writeUnauthorized(response, "TOKEN_EXPIRED");
                return;
            } catch (Exception e) {
                System.err.println("⚠️ Auth error: " + e.getMessage());
                writeUnauthorized(response, "AUTH_ERROR");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean shouldSkipAuthentication(String path) {
        return path.startsWith("/api/login")
                || path.startsWith("/setup/")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/swagger-resources")
                || path.startsWith("/webjars/");
    }

    private void writeUnauthorized(HttpServletResponse response, String code) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"error\":\"" + code + "\"}");
    }
}
