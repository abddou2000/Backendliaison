package com.example.login.Config;

import com.example.login.Models.*;
import com.example.login.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired private RoleRepository roleRepository;
    @Autowired private UtilisateurRepository utilisateurRepository;
    @Autowired private EmployeSimpleRepository employeSimpleRepository;
    @Autowired private AdministrateurRepository administrateurRepository;
    @Autowired private ConfigurateurRepository configurateurRepository;
    @Autowired private RhRepository rhRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        initializeRoles();
        if (!adminUserExists()) {
            createDefaultAdmin();
        }
    }

    private void initializeRoles() {
        List<String> types = List.of("ADMIN", "RH", "CONFIGURATEUR", "EMPLOYE");
        for (String t : types) {
            roleRepository.findByType(t)
                    .orElseGet(() -> roleRepository.save(new Role(t)));
        }
    }

    private boolean adminUserExists() {
        return utilisateurRepository.findByEmail("admin@company.com").isPresent();
    }

    @Transactional
    private void createDefaultAdmin() {
        Role adminRole = roleRepository.findByType("ADMIN")
                .orElseThrow(() -> new IllegalStateException("Role ADMIN non trouvé"));

        // 1) Création de l’utilisateur
        Utilisateur u = new Utilisateur();
        u.setRole(adminRole);
        u.setUsername("admin");
        u.setPasswordHash(passwordEncoder.encode("admin123"));
        u.setNom("Admin");
        u.setPrenom("Super");
        u.setEmail("admin@company.com");
        u.setEtatCompte("ACTIF");
        u.setDateCreation(LocalDateTime.now());
        utilisateurRepository.save(u);

        // 2) Création du profil Administrateur
        Administrateur a = new Administrateur();
        a.setUtilisateur(u);
        a.setNiveau("SUPER");
        administrateurRepository.save(a);
    }
}
