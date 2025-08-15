package com.example.login.Config;

import com.example.login.Models.Administrateur;
import com.example.login.Models.Role;
import com.example.login.Models.Utilisateur;
import com.example.login.Repositories.AdministrateurRepository;
import com.example.login.Repositories.RoleRepository;
import com.example.login.Repositories.UtilisateurRepository;
import com.example.login.Services.EmailService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final AdministrateurRepository administrateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public DatabaseInitializer(RoleRepository roleRepository,
                               UtilisateurRepository utilisateurRepository,
                               AdministrateurRepository administrateurRepository,
                               PasswordEncoder passwordEncoder,
                               EmailService emailService) {
        this.roleRepository = roleRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.administrateurRepository = administrateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Création des rôles
        Role adminRole = createRoleIfNotFound("ADMIN");
        createRoleIfNotFound("RH");
        createRoleIfNotFound("CONFIGURATEUR");
        createRoleIfNotFound("EMPLOYE");

        // Création de l'admin par défaut s'il n'existe pas
        if (utilisateurRepository.findByUsername("admin").isEmpty()) {

            Utilisateur adminUser = new Utilisateur();
            adminUser.setRole(adminRole);
            adminUser.setUsername("admin");
            adminUser.setNom("Admin");
            adminUser.setPrenom("Super");
            adminUser.setEmail("abdoubensalek7@gmail.com"); 

            // --- LOGIQUE SPÉCIFIQUE POUR L'ADMIN INITIAL ---
            String motDePasseInitial = "Admin123456!"; // Un mot de passe de départ solide
            adminUser.setPasswordHash(passwordEncoder.encode(motDePasseInitial));

            // L'admin est créé directement comme ACTIF
            adminUser.setEtatCompte(Utilisateur.EtatCompte.ACTIF);
            adminUser.setDateCreation(LocalDateTime.now());
            // Son mot de passe a une durée de vie normale de 3 mois
            adminUser.setDateExpirationMdp(LocalDateTime.now().plusMonths(3));

            Utilisateur savedUser = utilisateurRepository.save(adminUser);

            Administrateur adminProfile = new Administrateur();
            adminProfile.setId(savedUser.getId());
            adminProfile.setUtilisateur(savedUser);
            adminProfile.setNiveau("SUPER_ADMIN");

            // On lie le profil à l'utilisateur AVANT de sauvegarder l'utilisateur
            // (La sauvegarde du profil se fera par cascade)
            savedUser.setAdministrateur(adminProfile);
            utilisateurRepository.save(savedUser); // Sauvegarde finale avec la relation

            // On envoie un seul email de bienvenue à l'admin
            emailService.envoyerEmailSimple(savedUser.getEmail(), "Votre compte Administrateur a été créé",
                    "Bonjour " + savedUser.getPrenom() + ",\n\n" +
                            "Votre compte administrateur pour [Nom App] a été créé.\n" +
                            "Votre nom d'utilisateur est : " + savedUser.getUsername() + "\n" +
                            "Votre mot de passe est : " + motDePasseInitial);

            System.out.println(">>> Administrateur par défaut créé. ID: " + savedUser.getId());
        }
    }

    private Role createRoleIfNotFound(String name) {
        return roleRepository.findByType(name)
                .orElseGet(() -> roleRepository.save(new Role(name)));
    }
}