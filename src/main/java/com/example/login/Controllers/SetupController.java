package com.example.login.Controllers;

import com.example.login.Models.Administrateur;
import com.example.login.Models.Utilisateur;
import com.example.login.Services.AdministrateurService;
import com.example.login.Services.UtilisateurService;
import lombok.Data; // <-- IMPORTATION À AJOUTER
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/setup")
@CrossOrigin(origins = "*")
public class SetupController {

    @Value("${app.setup.enabled:true}")
    private boolean setupEnabled;

    private final UtilisateurService utilisateurService;
    private final AdministrateurService administrateurService;

    public SetupController(
            UtilisateurService utilisateurService,
            AdministrateurService administrateurService
    ) {
        this.utilisateurService = utilisateurService;
        this.administrateurService = administrateurService;
    }

    @PostMapping("/create-admin")
    @Transactional
    public ResponseEntity<String> createAdmin(@RequestBody AdminSetupRequest request) {
        if (!setupEnabled) {
            return ResponseEntity.status(403).body("Le setup est désactivé.");
        }

        // 1) Créer l'utilisateur via le service
        Utilisateur adminUser = new Utilisateur();
        adminUser.setUsername(request.getUsername());
        adminUser.setEmail(request.getEmail());
        adminUser.setNom(request.getNom());
        adminUser.setPrenom(request.getPrenom());
        // Le mot de passe sera généré automatiquement par le service

        Utilisateur savedUser = utilisateurService.create(adminUser, "ADMIN");

        // 2) Créer le profil Administrateur via son service
        Administrateur adminProfileData = new Administrateur();
        adminProfileData.setId(savedUser.getId());
        adminProfileData.setNiveau(request.getNiveau());

        administrateurService.create(adminProfileData);

        return ResponseEntity.ok("Admin créé avec succès pour l'utilisateur ID " + savedUser.getId() + ". Veuillez vérifier vos emails pour les identifiants.");
    }

    // DTO pour recevoir les données de création de l'admin
    @Data // <-- ANNOTATION MANQUANTE À AJOUTER ICI
    static class AdminSetupRequest {
        private String username;
        private String email;
        private String nom;
        private String prenom;
        private String niveau;
    }
}