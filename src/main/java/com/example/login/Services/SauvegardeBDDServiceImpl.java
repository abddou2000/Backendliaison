package com.example.login.Services;

import com.example.login.Models.Administrateur;
import com.example.login.Models.SauvegardeBDD;
import com.example.login.Models.Utilisateur;
import com.example.login.Repositories.AdministrateurRepository;
import com.example.login.Repositories.SauvegardeBDDRepository;
import com.example.login.Repositories.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder; // <-- Importation ajoutée
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SauvegardeBDDServiceImpl implements SauvegardeBDDService {

    private final SauvegardeBDDRepository sauvegardeRepository;
    private final AdministrateurRepository administrateurRepository;
    private final UtilisateurRepository utilisateurRepository; // <-- Dépendance ajoutée
    private final PasswordEncoder passwordEncoder;
    public SauvegardeBDDServiceImpl(SauvegardeBDDRepository sauvegardeRepository,
                                    AdministrateurRepository administrateurRepository,
                                    UtilisateurRepository utilisateurRepository,
                                    PasswordEncoder passwordEncoder) {
        this.sauvegardeRepository = sauvegardeRepository;
        this.administrateurRepository = administrateurRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<SauvegardeBDD> getAllSauvegardes() {
        return sauvegardeRepository.findAll();
    }

    @Override
    @Transactional
    public SauvegardeBDD creerSauvegardeManuelle(Long adminId) {
        Administrateur admin = administrateurRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Administrateur non trouvé avec l'ID: " + adminId));

        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String fileName = "backup_manuel_" + timestamp + ".sql";

        SauvegardeBDD sauvegarde = new SauvegardeBDD();
        sauvegarde.setIdSauvegarde(UUID.randomUUID().toString());
        sauvegarde.setNomFichier(fileName);
        sauvegarde.setEmplacement("/var/data/backups/" + fileName);
        sauvegarde.setDateSauvegarde(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
        sauvegarde.setCreePar(admin);

        return sauvegardeRepository.save(sauvegarde);
    }

    // --- MÉTHODE MODIFIÉE POUR VALIDER LE MOT DE PASSE ---
    @Override
    @Transactional
    public void nettoyerToutesLesArchives(String adminUsername, String password) {
        // 1. Trouver l'utilisateur admin
        Utilisateur adminUser = utilisateurRepository.findByUsername(adminUsername)
                .orElseThrow(() -> new RuntimeException("Administrateur non trouvé."));

        // 2. Vérifier si le mot de passe fourni correspond au hash en base de données
        if (!passwordEncoder.matches(password, adminUser.getPasswordHash())) {
            // Lève une exception si le mot de passe est incorrect.
            // Un @ControllerAdvice interceptera cela pour renvoyer une erreur 400 Bad Request.
            throw new IllegalArgumentException("Mot de passe administrateur incorrect.");
        }

        // 3. Si le mot de passe est correct, tout supprimer.
        sauvegardeRepository.deleteAll();
    }
}