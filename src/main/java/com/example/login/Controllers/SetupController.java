package com.example.login.Controllers;

import com.example.login.Models.Administrateur;
import com.example.login.Models.EmployeSimple;
import com.example.login.Models.Role;
import com.example.login.Models.Utilisateur;
import com.example.login.Repositories.AdministrateurRepository;
import com.example.login.Repositories.EmployeSimpleRepository;
import com.example.login.Repositories.RoleRepository;
import com.example.login.Repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/setup")
public class SetupController {

    @Value("${app.setup.enabled:false}")
    private boolean setupEnabled;

    @Value("${app.setup.key:}")
    private String setupKey;

    private final RoleRepository roleRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final EmployeSimpleRepository employeRepository;
    private final AdministrateurRepository administrateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SetupController(
            RoleRepository roleRepository,
            UtilisateurRepository utilisateurRepository,
            EmployeSimpleRepository employeRepository,
            AdministrateurRepository administrateurRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.employeRepository = employeRepository;
        this.administrateurRepository = administrateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/create-admin")
    @Transactional
    public ResponseEntity<String> createAdmin(
            @RequestBody EmployeSimple employeData,
            @RequestParam(required = false) String key
    ) {
        // 1) Sécurité : autorisé seulement si setupEnabled ou bonne clé
        if (!setupEnabled && (setupKey.isEmpty() || !setupKey.equals(key))) {
            return ResponseEntity.status(403).body("Setup not enabled or invalid key");
        }

        // 2) Créer les rôles fixes s'ils n'existent pas
        List<String> types = List.of("ADMIN", "RH", "CONFIGURATEUR", "EMPLOYE");
        for (String t : types) {
            roleRepository.findByType(t)
                    .orElseGet(() -> roleRepository.save(new Role(t)));
        }

        // 3) Récupérer le Role ADMIN
        Role adminRole = roleRepository.findByType("ADMIN")
                .orElseThrow(() -> new IllegalStateException("Role ADMIN non trouvé"));

        // 4) Créer et sauvegarder l'entité Utilisateur
        Utilisateur u = employeData.getUtilisateur();
        if (u == null) {
            return ResponseEntity.badRequest().body("Utilisateur data is required");
        }
        u.setRole(adminRole);
        // Encoder mot de passe en BCrypt
        u.setPasswordHash(passwordEncoder.encode(u.getPasswordHash()));
        // Initialiser autres champs
        u.setEtatCompte("ACTIF");
        u.setDateCreation(LocalDateTime.now());
        u.setDateModification(null);
        Utilisateur savedUser = utilisateurRepository.save(u);

        // 5) Créer et sauvegarder l'EmployeSimple
        employeData.setUtilisateur(savedUser);
        EmployeSimple savedEmp = employeRepository.save(employeData);

        // 6) Créer et sauvegarder le profil Administrateur
        Administrateur admin = new Administrateur();
        admin.setUtilisateur(savedUser);
        admin.setNiveau("SUPER");
        administrateurRepository.save(admin);

        return ResponseEntity.ok("Admin created successfully with user id " + savedUser.getId());
    }
}
