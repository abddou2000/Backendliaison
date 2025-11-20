package com.example.login.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("=== JWT FILTER DEBUG ===");
        System.out.println("Request path: " + request.getRequestURI());

        try {
            // 1️⃣ Extraire le token JWT du header Authorization
            String jwt = parseJwt(request);

            if (jwt != null && jwtUtil.validateJwtToken(jwt)) {
                // 2️⃣ Extraire le username du token
                String username = jwtUtil.getUsernameFromToken(jwt);
                System.out.println("JWT valid for user: " + username);

                // 3️⃣ Charger les détails de l'utilisateur
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // 4️⃣ Extraire le rôle actif et la liste des rôles du token
                String activeRole = jwtUtil.getActiveRoleFromToken(jwt);
                List<String> roles = jwtUtil.getRolesFromToken(jwt);

                System.out.println("Active role: " + activeRole);
                System.out.println("All roles: " + roles);

                // 5️⃣ Créer les authorities (CRITIQUE pour Spring Security)
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList());

                // Ajouter le rôle actif si pas déjà présent
                if (activeRole != null && !activeRole.isBlank()) {
                    SimpleGrantedAuthority activeAuthority = new SimpleGrantedAuthority("ROLE_" + activeRole);
                    if (!authorities.contains(activeAuthority)) {
                        authorities.add(activeAuthority);
                    }
                }

                System.out.println("Granted authorities: " + authorities);

                // 6️⃣ Créer l'objet d'authentification avec les authorities
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,  // credentials (pas besoin, déjà authentifié)
                                authorities  // ⚠️ CRITIQUE: doit contenir les rôles
                        );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 7️⃣ Définir l'authentification dans le SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);

                System.out.println("✅ Authentication set in SecurityContext");
                System.out.println("Is authenticated: " + authentication.isAuthenticated());
                System.out.println("Authorities: " + authentication.getAuthorities());

            } else {
                System.out.println("❌ No valid JWT found");
            }

        } catch (Exception e) {
            System.err.println("❌ Cannot set user authentication: " + e.getMessage());
            e.printStackTrace();
        }

        // 8️⃣ Continuer la chaîne de filtres
        filterChain.doFilter(request, response);
    }

    /**
     * Extrait le token JWT du header "Authorization: Bearer <token>"
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            String token = headerAuth.substring(7);
            System.out.println("Token extracted: " + token.substring(0, Math.min(20, token.length())) + "...");
            return token;
        }

        System.out.println("No Bearer token found in Authorization header");
        return null;
    }
}