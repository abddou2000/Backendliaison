package com.example.login.Controllers;

import com.example.login.Models.ActivityLog;
import com.example.login.Models.Role; // Import ajouté
import com.example.login.Models.Utilisateur;
import com.example.login.Repositories.UtilisateurRepository;
import com.example.login.Security.JwtUtil;
import com.example.login.Services.ActivityLogService;
import com.example.login.Services.AffectationRoleUtilisateurService;
import com.example.login.Services.UtilisateurService; // Import ajouté
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
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UtilisateurRepository utilisateurRepository;
    private final AffectationRoleUtilisateurService affectationService;

    @Autowired private ActivityLogService activityLogService;
    @Autowired private UtilisateurService utilisateurService; // <-- ÉTAPE 1 : Service injecté
    @Autowired(required = false) private HttpServletRequest request;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtUtil jwtUtil,
            UtilisateurRepository utilisateurRepository,
            AffectationRoleUtilisateurService affectationService
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.utilisateurRepository = utilisateurRepository;
        this.affectationService = affectationService;
    }

    // ========= LOGIN (Option A : rôle actif par défaut, sélection possible après via /auth/switch-role) =========
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest req) {
        System.out.println("=== DEBUG LOGIN === user=" + req.getUsername());
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            Utilisateur user = utilisateurRepository.findByUsername(req.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable après authentification."));

            // États de compte
            if (user.getEtatCompte() == Utilisateur.EtatCompte.BLOQUE) {
                logActivity(user, "Tentative login - compte bloqué",
                        ActivityLog.ActivityType.CONNEXION, ActivityLog.ActivityStatus.ERROR, null);
                return ResponseEntity.status(403).body("Votre compte est bloqué. Veuillez contacter un administrateur.");
            }
            if (user.getEtatCompte() == Utilisateur.EtatCompte.EN_ATTENTE_CHANGEMENT_MDP
                    || (user.getDateExpirationMdp() != null && user.getDateExpirationMdp().isBefore(LocalDateTime.now()))) {

                if (user.getEtatCompte() != Utilisateur.EtatCompte.EN_ATTENTE_CHANGEMENT_MDP) {
                    user.setEtatCompte(Utilisateur.EtatCompte.BLOQUE);
                    utilisateurRepository.save(user);
                }
                logActivity(user, "Login refusé - mot de passe expiré",
                        ActivityLog.ActivityType.CONNEXION, ActivityLog.ActivityStatus.WARNING, null);

                Map<String, Object> response = new HashMap<>();
                response.put("action", "CHANGER_MDP");
                response.put("message", "Votre mot de passe doit être changé.");
                response.put("userId", user.getId());
                return ResponseEntity.status(403).body(response);
            }

            // Rôles possibles (pivot + rôle principal si présent)
            List<String> rolesFromPivot = affectationService.getRoleTypesOfUser(user.getId());
            String primary = (user.getRole() != null ? user.getRole().getType() : null);
            LinkedHashSet<String> rolesSet = new LinkedHashSet<>();
            if (primary != null && !primary.isBlank()) rolesSet.add(primary);
            rolesSet.addAll(rolesFromPivot);
            List<String> roles = new ArrayList<>(rolesSet);

            if (roles.isEmpty()) {
                return ResponseEntity.status(403).body("Aucun rôle attribué à cet utilisateur.");
            }

            // ======================================================================================
            // <-- ÉTAPE 2 : Ajustement du rôle actif
            // ✅ Déterminer automatiquement le rôle prioritaire à activer
            Role roleActif = utilisateurService.getRolePrioritaire(user);
            String activeRole = (roleActif != null ? roleActif.getType() :
                    (primary != null && roles.contains(primary) ? primary : roles.get(0)));
            // ======================================================================================


            // Token avec rôle actif + liste des rôles
            final UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());
            final String token = jwtUtil.generateToken(userDetails.getUsername(), activeRole, roles);

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", user.getId());
            userInfo.put("nom", user.getNom());
            userInfo.put("prenom", user.getPrenom());
            userInfo.put("email", user.getEmail());
            userInfo.put("etatCompte", user.getEtatCompte());

            Map<String, Object> body = new HashMap<>();
            body.put("token", token);
            body.put("user", userInfo);
            body.put("roles", roles);
            body.put("activeRole", activeRole);
            body.put("message", "Connexion réussie");

            logActivity(user, "Connexion à l'application (role=" + activeRole + ")",
                    ActivityLog.ActivityType.CONNEXION, ActivityLog.ActivityStatus.SUCCESS, null);

            return ResponseEntity.ok(body);

        } catch (BadCredentialsException ex) {
            tryLogFailedLogin(req.getUsername(), "Mot de passe incorrect");
            return ResponseEntity.status(401).body("Nom d'utilisateur ou mot de passe invalide.");
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body("Erreur interne lors de l'authentification : " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body("Erreur inattendue : " + ex.getMessage());
        }
    }

    // ========= SWITCH ROLE (protégé : le filtre NE DOIT PAS ignorer /api/auth/switch-role) =========
    @PostMapping("/auth/switch-role")
    public ResponseEntity<?> switchRole(@RequestBody SwitchRoleRequest req) {
        String username = (SecurityContextHolder.getContext().getAuthentication() != null)
                ? SecurityContextHolder.getContext().getAuthentication().getName()
                : null;
        if (username == null) return ResponseEntity.status(401).body("Non authentifié.");

        Utilisateur user = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable."));

        // Rôles autorisés
        List<String> roles = new ArrayList<>(new LinkedHashSet<>(affectationService.getRoleTypesOfUser(user.getId())));
        String primary = (user.getRole() != null ? user.getRole().getType() : null);
        if (primary != null && !primary.isBlank() && !roles.contains(primary)) roles.add(0, primary);

        String target = req.getRole();
        if (target == null || target.isBlank() || !roles.contains(target)) {
            return ResponseEntity.status(403).body("Rôle non autorisé pour cet utilisateur.");
        }

        final String token = jwtUtil.generateToken(username, target, roles);

        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("activeRole", target);
        body.put("roles", roles);

        logActivity(user, "SWITCH_ROLE vers " + target,
                ActivityLog.ActivityType.CONNEXION, ActivityLog.ActivityStatus.SUCCESS, null);

        return ResponseEntity.ok(body);
    }

    // ========= Logging utils =========
    private void tryLogFailedLogin(String username, String reason) {
        try {
            Utilisateur user = utilisateurRepository.findByUsername(username).orElse(null);
            if (user != null) {
                logActivity(user, "Tentative de login échouée - " + reason,
                        ActivityLog.ActivityType.CONNEXION, ActivityLog.ActivityStatus.ERROR, null);
            } else {
                logActivityWithoutUser(username, "Tentative de login échouée - utilisateur inexistant",
                        ActivityLog.ActivityType.CONNEXION, ActivityLog.ActivityStatus.ERROR);
            }
        } catch (Exception ignore) {}
    }

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
            log.setIpAddress(getClientIpAddress());
            if (this.request != null) log.setUserAgent(this.request.getHeader("User-Agent"));
            log.setDetails(action);
            activityLogService.create(log);
        } catch (Exception e) {
            System.err.println("Log activity error: " + e.getMessage());
        }
    }

    private void logActivityWithoutUser(String username, String action, ActivityLog.ActivityType type, ActivityLog.ActivityStatus status) {
        try {
            ActivityLog log = new ActivityLog();
            log.setTimestamp(LocalDateTime.now());
            log.setUserId(0L);
            log.setUserName("Utilisateur inconnu");
            log.setUserEmail(username);
            log.setAction(action);
            log.setType(type);
            log.setStatus(status);
            log.setTarget(username);
            log.setIpAddress(getClientIpAddress());
            if (this.request != null) log.setUserAgent(this.request.getHeader("User-Agent"));
            log.setDetails(action + " - Username: " + username);
            activityLogService.create(log);
        } catch (Exception e) {
            System.err.println("Log activity error: " + e.getMessage());
        }
    }

    private String getClientIpAddress() {
        if (this.request == null) return "0.0.0.0";
        String xForwardedFor = this.request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) return xForwardedFor.split(",")[0].trim();
        String remoteAddr = this.request.getRemoteAddr();
        return remoteAddr != null ? remoteAddr : "0.0.0.0";
    }

    // ========= DTOs =========
    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }

    @Data
    public static class SwitchRoleRequest {
        private String role;
    }
}