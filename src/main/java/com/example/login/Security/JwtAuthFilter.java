package com.example.login.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
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
                // Charger l’utilisateur (pour Principal & vérifs de base)
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Valider la signature/exp du token
                if (!jwtUtil.validateJwtToken(token)) {
                    writeUnauthorized(response, "TOKEN_INVALID");
                    return;
                }

                // 🔑 Rôle ACTIF depuis le token → une seule authority
                String activeRole = jwtUtil.getActiveRoleFromToken(token);
                if (activeRole == null || activeRole.isBlank()) {
                    writeUnauthorized(response, "ACTIVE_ROLE_MISSING");
                    return;
                }

                var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + activeRole));

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            } catch (UsernameNotFoundException e) {
                writeUnauthorized(response, "USER_NOT_FOUND");
                return;
            } catch (ExpiredJwtException eje) {
                writeUnauthorized(response, "TOKEN_EXPIRED");
                return;
            } catch (Exception e) {
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
