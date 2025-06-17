package com.example.login.controllers;

import com.example.login.models.EmployeSimple;
import com.example.login.models.Rh;
import com.example.login.models.LoginRequest;
import com.example.login.repositories.EmployeSimpleRepository;
import com.example.login.repositories.RhRepository;
import com.example.login.security.JwtUtil;
import com.example.login.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final EmployeSimpleRepository employeRepository;
    private final RhRepository rhRepository;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            EmployeSimpleRepository employeRepository,
            RhRepository rhRepository,
            CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.employeRepository = employeRepository;
        this.rhRepository = rhRepository;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            // 1) Authentifier
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 2) Charger les détails utilisateur (Employee ou RH)
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

            // 3) Générer le JWT
            String jwt = jwtUtil.generateToken(userDetails);

            // 4) Préparer la réponse selon le rôle
            Map<String, Object> userResponse = new HashMap<>();
            String username = loginRequest.getUsername();
            boolean isRh = userDetails.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_RH") || auth.getAuthority().equals("RH"));

            if (isRh) {
                Rh rh = rhRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("RH introuvable: " + username));
                userResponse.put("id", rh.getIdRh());
                userResponse.put("nom", rh.getNom());
                userResponse.put("prenom", rh.getPrenom());
                userResponse.put("email", rh.getEmail());
                userResponse.put("role", rh.getRole().getNomRole());
            } else {
                // Utiliser les méthodes avec rôle pour récupérer l'employé
                EmployeSimple emp = employeRepository.findByEmailProWithRole(username)
                        .or(() -> employeRepository.findByEmailPersoWithRole(username))
                        .orElseThrow(() -> new UsernameNotFoundException("Employé introuvable: " + username));
                userResponse.put("id", emp.getIdEmploye());
                userResponse.put("nom", emp.getNom());
                userResponse.put("prenom", emp.getPrenom());
                userResponse.put("email", emp.getEmailPro());
                userResponse.put("role", emp.getRole().getNomRole());
            }

            // 5) Retourne token + user info
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("user", userResponse);
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException | UsernameNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Échec de la connexion : Email ou mot de passe incorrect.");
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la connexion : " + ex.getMessage());
        }
    }
}
