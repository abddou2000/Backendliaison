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
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            Utilisateur user = utilisateurRepository
                    .findByUsername(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable après authentification réussie."));

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

            if (user.getEtatCompte() == Utilisateur.EtatCompte.BLOQUE) {
                return ResponseEntity.status(403).body("Votre compte est bloqué. Veuillez contacter un administrateur.");
            }

            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            final String token = jwtUtil.generateToken(userDetails);

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

        } catch (BadCredentialsException ex) { // <-- BLOC CATCH QUI MANQUAIT
            return ResponseEntity.status(401).body("Nom d'utilisateur ou mot de passe invalide.");
        } catch (AuthenticationException ex) { // <-- BLOC CATCH QUI MANQUAIT
            return ResponseEntity.status(500).body("Erreur interne lors de l'authentification : " + ex.getMessage());
        }
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }
}