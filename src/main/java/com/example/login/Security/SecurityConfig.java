package com.example.login.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * ✅ IMPORTANT :
     * Utilise un DelegatingPasswordEncoder qui comprend les préfixes {bcrypt}.
     * Cela règle l'erreur "Encoded password does not look like BCrypt".
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // par défaut : bcrypt, mais supporte aussi {bcrypt}hash
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        System.out.println("DaoAuthenticationProvider configured with CustomUserDetailsService + DelegatingPasswordEncoder");
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Endpoints publics (sans authentification)
                        .requestMatchers("/api/login", "/api/register").permitAll()
                        .requestMatchers("/api/utilisateurs", "/api/utilisateurs/**").permitAll()
                        .requestMatchers("/api/roles", "/api/roles/**").permitAll()
                        .requestMatchers("/setup/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()

                        // Endpoints protégés pour employés (avec authentification JWT)
                        .requestMatchers("/api/employes/**").authenticated()

                        // Endpoints protégés par rôle
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/rh/**").hasAnyRole("ADMIN", "RH")
                        .requestMatchers("/api/configurateur/**").hasAnyRole("ADMIN", "CONFIGURATEUR")

                        // Tout le reste nécessite une authentification
                        .anyRequest().authenticated()
                )
                // Provider + filtre JWT
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        // Pour accéder à la H2 console en dev
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        // Autoriser les origines frontend
        cfg.setAllowedOrigins(List.of(
                "http://localhost:3000",    // Next.js dev
                "http://localhost:4200",    // Angular dev
                "http://localhost:5173"     // Vite dev
        ));

        cfg.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}