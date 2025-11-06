package com.example.login.Controllers;

import com.example.login.Models.ActivityLog;
import com.example.login.Models.Utilisateur;
import com.example.login.Repositories.UtilisateurRepository;
import com.example.login.Security.JwtUtil;
import com.example.login.Services.ActivityLogService;
import jakarta.servlet.http.HttpServletRequest;
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
    private ActivityLogService activityLogService; // ✅ Ajout du service de log

    @Autowired(required = false)
    private HttpServletRequest request; // ✅ Pour récupérer l'IP et le User-Agent

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

                // ✅ Logger la connexion réussie
                logActivity(
                        user,
                        "Connexion à l'application",
                        ActivityLog.ActivityType.CONNEXION,
                        ActivityLog.ActivityStatus.SUCCESS,
                        null
                );

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

                // ✅ Logger la tentative de connexion avec mot de passe expiré
                logActivity(
                        user,
                        "Tentative de connexion avec mot de passe expiré",
                        ActivityLog.ActivityType.CONNEXION,
                        ActivityLog.ActivityStatus.WARNING,
                        null
                );

                Map<String, Object> response = new HashMap<>();
                response.put("action", "CHANGER_MDP");
                response.put("message", "Votre mot de passe doit être changé.");
                response.put("userId", user.getId());
                return ResponseEntity.status(403).body(response);
            }

            // Gestion des comptes bloqués
            if (user.getEtatCompte() == Utilisateur.EtatCompte.BLOQUE) {
                // ✅ Logger la tentative de connexion avec compte bloqué
                logActivity(
                        user,
                        "Tentative de connexion sur compte bloqué",
                        ActivityLog.ActivityType.CONNEXION,
                        ActivityLog.ActivityStatus.ERROR,
                        null
                );

                return ResponseEntity.status(403).body("Votre compte est bloqué. Veuillez contacter un administrateur.");
            }

            // Cas par défaut (ne devrait pas arriver)
            return ResponseEntity.status(403).body("État de compte non reconnu.");

        } catch (BadCredentialsException ex) {
            System.err.println("Bad credentials for username: " + request.getUsername());

            // ✅ Logger la tentative de connexion échouée (mauvais mot de passe)
            try {
                Utilisateur user = utilisateurRepository.findByUsername(request.getUsername()).orElse(null);
                if (user != null) {
                    logActivity(
                            user,
                            "Tentative de connexion échouée - Mot de passe incorrect",
                            ActivityLog.ActivityType.CONNEXION,
                            ActivityLog.ActivityStatus.ERROR,
                            null
                    );
                } else {
                    // Si l'utilisateur n'existe pas, on log avec des infos génériques
                    logActivityWithoutUser(
                            request.getUsername(),
                            "Tentative de connexion échouée - Utilisateur inexistant",
                            ActivityLog.ActivityType.CONNEXION,
                            ActivityLog.ActivityStatus.ERROR
                    );
                }
            } catch (Exception logEx) {
                System.err.println("Erreur lors du logging: " + logEx.getMessage());
            }

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

    // ✅ MÉTHODE POUR LOGGER LES ACTIVITÉS DE CONNEXION
    private void logActivity(Utilisateur user, String action, ActivityLog.ActivityType type, ActivityLog.ActivityStatus status, String target) {
        try {
            ActivityLog log = new ActivityLog();
            log.setTimestamp(LocalDateTime.now());
            log.setUserId(user.getId());
            log.setUserName(user.getPrenom() + " " + user.getNom());
            log.setUserEmail(user.getEmail());
            log.setAction(action);
            log.setType(type);
            log.setStatus(status);
            log.setTarget(target);

            // Récupérer l'adresse IP
            log.setIpAddress(getClientIpAddress());

            // Récupérer le User-Agent
            if (this.request != null) {
                log.setUserAgent(this.request.getHeader("User-Agent"));
            }

            log.setDetails(action);

            activityLogService.create(log);
        } catch (Exception e) {
            System.err.println("⚠️ Erreur lors du logging de l'activité: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ✅ MÉTHODE POUR LOGGER SANS UTILISATEUR (tentatives avec username inexistant)
    private void logActivityWithoutUser(String username, String action, ActivityLog.ActivityType type, ActivityLog.ActivityStatus status) {
        try {
            ActivityLog log = new ActivityLog();
            log.setTimestamp(LocalDateTime.now());
            log.setUserId(0L); // ID 0 pour utilisateur inexistant
            log.setUserName("Utilisateur inconnu");
            log.setUserEmail(username); // On met le username dans l'email pour traçabilité
            log.setAction(action);
            log.setType(type);
            log.setStatus(status);
            log.setTarget(username);

            // Récupérer l'adresse IP
            log.setIpAddress(getClientIpAddress());

            // Récupérer le User-Agent
            if (this.request != null) {
                log.setUserAgent(this.request.getHeader("User-Agent"));
            }

            log.setDetails(action + " - Username: " + username);

            activityLogService.create(log);
        } catch (Exception e) {
            System.err.println("⚠️ Erreur lors du logging de l'activité: " + e.getMessage());
        }
    }

    // Récupérer l'adresse IP du client
    private String getClientIpAddress() {
        if (this.request == null) {
            return "0.0.0.0";
        }

        String xForwardedFor = this.request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        String remoteAddr = this.request.getRemoteAddr();
        return remoteAddr != null ? remoteAddr : "0.0.0.0";
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }
}