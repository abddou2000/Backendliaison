package com.example.login.Controllers;

import com.example.login.Models.EmployeSimple;
import com.example.login.Repositories.EmployeSimpleRepository;
import com.example.login.Security.JwtUtil;
import com.example.login.Security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final EmployeSimpleRepository employeRepository;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            EmployeSimpleRepository employeRepository,
            CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.employeRepository = employeRepository;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            // 1. Authentifier l'utilisateur
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 2. Charger UserDetails avec les rôles
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

            // 3. Générer JWT avec rôles
            String jwt = jwtUtil.generateToken(userDetails);

            // 4. Retrouver l'utilisateur en base pour info frontend
            EmployeSimple employe = employeRepository.findByEmailPro(loginRequest.getUsername())
                    .or(() -> employeRepository.findByEmailPerso(loginRequest.getUsername()))
                    .orElseThrow(() -> new Exception("Utilisateur introuvable"));

            // 5. Construire la réponse utilisateur
            Map<String, Object> userResponse = new HashMap<>();
            userResponse.put("id", employe.getIdEmploye());
            userResponse.put("nom", employe.getNom());
            userResponse.put("prenom", employe.getPrenom());
            userResponse.put("email", employe.getEmailPro());
            userResponse.put("role", employe.getRole().getNomRole()); // 👍 nomRole (lisible)

            // 6. Retourner token + info utilisateur
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("user", userResponse);

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Échec de la connexion : Email ou mot de passe incorrect.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la connexion : " + e.getMessage());
        }
    }
}
