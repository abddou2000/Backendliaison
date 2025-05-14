package com.example.login.Controllers;

import com.example.login.Models.EmployeSimple;
import com.example.login.Repositories.EmployeSimpleRepository;
import com.example.login.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, 
                         JwtUtil jwtUtil, 
                         EmployeSimpleRepository employeRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.employeRepository = employeRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
            String jwt = jwtUtil.generateToken(loginRequest.getUsername());
            
            // Get user details from the database
            EmployeSimple employe = employeRepository.findByEmailPro(loginRequest.getUsername())
                .orElseThrow(() -> new Exception("User not found"));
                

            Map<String, Object> userResponse = new HashMap<>();
            userResponse.put("id", employe.getIdEmploye());
            userResponse.put("nom", employe.getNom());
            userResponse.put("prenom", employe.getPrenom());
            userResponse.put("email", employe.getEmailPro());
            userResponse.put("role", employe.getRole().getIdRole());

            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("user", userResponse);
            
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Échec de la connexion: Email ou mot de passe incorrect.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la connexion: " + e.getMessage());
        }
    }
}