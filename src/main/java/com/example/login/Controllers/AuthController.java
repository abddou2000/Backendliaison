package com.example.login.Controllers;

import com.example.login.Models.Utilisateur;
import com.example.login.Repositories.UtilisateurRepository;
import com.example.login.Security.JwtUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
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
        System.out.println("=== DEBUG LOGIN ===");
        System.out.println("Login attempt for username: " + request.getUsername());

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            System.out.println("Authentication successful");

            Utilisateur user = utilisateurRepository
                    .findByUsername(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable après authentification réussie."));

            System.out.println("User found: " + user.getUsername() + ", Role: " +
                    (user.getRole() != null ? user.getRole().getType() : "NULL"));
            System.out.println("User state: " + user.getEtatCompte());

            // MODIFICATION : Permettre la connexion des comptes ACTIFS directement
            if (user.getEtatCompte() == Utilisateur.EtatCompte.ACTIF) {
                // Compte actif - connexion autorisée pour tous les rôles
                final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
                final String token = jwtUtil.generateToken(userDetails);

                System.out.println("Token generated for active user: " + token.substring(0, 20) + "...");

                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("id",     user.getId());
                userInfo.put("nom",    user.getNom());
                userInfo.put("prenom", user.getPrenom());
                userInfo.put("email",  user.getEmail());
                userInfo.put("role",   user.getRole().getType());
                userInfo.put("etatCompte", user.getEtatCompte());

                Map<String, Object> body = new HashMap<>();
                body.put("token", token);
                body.put("user",  userInfo);
                body.put("message", "Connexion réussie");

                System.out.println("Login successful for " + user.getRole().getType() + " user");
                return ResponseEntity.ok(body);
            }

            // Gestion des comptes en attente de changement de mot de passe
            if (user.getEtatCompte() == Utilisateur.EtatCompte.EN_ATTENTE_CHANGEMENT_MDP ||
                    (user.getDateExpirationMdp() != null && user.getDateExpirationMdp().isBefore(LocalDateTime.now()))) {

                if (user.getEtatCompte() != Utilisateur.EtatCompte.EN_ATTENTE_CHANGEMENT_MDP) {
                    user.setEtatCompte(Utilisateur.EtatCompte.BLOQUE);
                    utilisateurRepository.save(user);
                }

                Map<String, Object> response = new HashMap<>();
                response.put("action", "CHANGER_MDP");
                response.put("message", "Votre mot de passe doit être changé.");
                response.put("userId", user.getId());
                return ResponseEntity.status(403).body(response);
            }

            // Gestion des comptes bloqués
            if (user.getEtatCompte() == Utilisateur.EtatCompte.BLOQUE) {
                return ResponseEntity.status(403).body("Votre compte est bloqué. Veuillez contacter un administrateur.");
            }

            // Cas par défaut (ne devrait pas arriver)
            return ResponseEntity.status(403).body("État de compte non reconnu.");

        } catch (BadCredentialsException ex) {
            System.err.println("Bad credentials for username: " + request.getUsername());
            return ResponseEntity.status(401).body("Nom d'utilisateur ou mot de passe invalide.");
        } catch (AuthenticationException ex) {
            System.err.println("Authentication error: " + ex.getMessage());
            ex.printStackTrace();
            return ResponseEntity.status(500).body("Erreur interne lors de l'authentification : " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Unexpected error during login: " + ex.getMessage());
            ex.printStackTrace();
            return ResponseEntity.status(500).body("Erreur inattendue : " + ex.getMessage());
        }
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }
}