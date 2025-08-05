package com.example.login.Controllers;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.login.Models.Utilisateur;
import com.example.login.Repositories.UtilisateurRepository;
import com.example.login.Security.JwtUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Contrôleur gérant l'authentification des utilisateurs et la génération de JWT.
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtUtil jwtUtil,
            UtilisateurRepository utilisateurRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.utilisateurRepository = utilisateurRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest request) {
        try {
            // 1) Authentifier l’utilisateur
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            // 2) Charger les détails (incluant les rôles)
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

            // 3) Générer le token JWT
            String token = jwtUtil.generateToken(userDetails);

            // 4) Récupérer l’entité Utilisateur
            Utilisateur user = utilisateurRepository
                    .findByUsername(request.getUsername())
                    .or(() -> utilisateurRepository.findByEmail(request.getUsername()))
                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));

            // 5) Préparer la réponse JSON
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id",     user.getId());
            userInfo.put("nom",    user.getNom());
            userInfo.put("prenom", user.getPrenom());
            userInfo.put("email",  user.getEmail());
            userInfo.put("role",   user.getRole().getType());

            Map<String, Object> body = new HashMap<>();
            body.put("token", token);
            body.put("user",  userInfo);

            return ResponseEntity.ok(body);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401)
                    .body("Identifiants invalides");
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(500)
                    .body("Erreur d'authentification : " + ex.getMessage());
        }
    }

    /** DTO pour la requête de connexion. */
    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }
}
