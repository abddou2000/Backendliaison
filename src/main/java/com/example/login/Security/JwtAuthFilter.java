package com.example.login.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

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
            System.out.println("Skipping authentication for path: " + path);
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        System.out.println("Authorization header: " + header);

        String token = null;
        String username = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            System.out.println("Token extracted: " + token.substring(0, Math.min(20, token.length())) + "...");

            try {
                username = jwtUtil.getUsernameFromToken(token);
                System.out.println("Username from token: " + username);
            } catch (Exception e) {
                System.err.println("Error extracting username from token: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No Bearer token found in Authorization header");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("Loading user details for username: " + username);

            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println("UserDetails loaded: " + userDetails.getUsername());
                System.out.println("UserDetails authorities: " + userDetails.getAuthorities());

                // CORRECTION: Utiliser le bon nom de méthode
                if (jwtUtil.validateJwtToken(token)) {
                    System.out.println("Token is valid, setting authentication");

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities()
                            );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    System.out.println("Authentication set successfully");
                } else {
                    System.err.println("Token validation failed");
                }
            } catch (Exception e) {
                System.err.println("Error loading user details: " + e.getMessage());
                e.printStackTrace();
            }
        } else if (username == null) {
            System.out.println("Username is null");
        } else {
            System.out.println("Authentication already exists in SecurityContext");
        }

        filterChain.doFilter(request, response);
    }

    private boolean shouldSkipAuthentication(String path) {
        return path.startsWith("/api/login")
                || path.startsWith("/setup/")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui");
    }
}