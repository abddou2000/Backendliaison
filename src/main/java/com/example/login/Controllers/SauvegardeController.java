package com.example.login.Controllers;

import com.example.login.DTO.SauvegardeDTO;
import com.example.login.DTO.SauvegardeStatsDTO;
import com.example.login.Models.Administrateur;
import com.example.login.Models.SauvegardeBDD;
import com.example.login.Repositories.AdministrateurRepository;
import com.example.login.Repositories.SauvegardeBDDRepository; // ✅ CORRIGÉ : Nom du repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sauvegardes")
@CrossOrigin(origins = "*")
public class SauvegardeBDDController {

    @Autowired
    private SauvegardeBDDRepository sauvegardeRepository; // ✅ CORRIGÉ : Nom du repository

    @Autowired
    private SauvegardeService sauvegardeService;

    @Autowired
    private AdministrateurRepository administrateurRepository;

    private Administrateur getAuthenticatedAdmin() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                String email = authentication.getName();
                return administrateurRepository.findByEmail(email).orElse(null);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'admin: " + e.getMessage());
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<List<SauvegardeDTO>> getAllSauvegardes() {
        try {
            List<SauvegardeBDD> sauvegardes = sauvegardeRepository.findAllByOrderByDateSauvegardeDesc();
            List<SauvegardeDTO> dtos = sauvegardes.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<SauvegardeStatsDTO> getStats() {
        try {
            long totalBackups = sauvegardeRepository.count();

            List<SauvegardeBDD> sauvegardes = sauvegardeRepository.findAll();
            double totalSizeMB = sauvegardes.stream()
                    .filter(s -> s.getTailleFichier() != null)
                    .mapToLong(SauvegardeBDD::getTailleFichier)
                    .sum() / (1024.0 * 1024.0);
            String totalSize = String.format("%.1f MB", totalSizeMB);

            Optional<SauvegardeBDD> derniere = Optional.ofNullable(sauvegardeRepository.findTopByOrderByDateSauvegardeDesc());
            String lastBackup = derniere.map(s -> {
                long minutes = ChronoUnit.MINUTES.between(s.getDateSauvegarde(), LocalDateTime.now());
                if (minutes < 60) return "Il y a " + minutes + " min";
                long hours = minutes / 60;
                if (hours < 24) return "Il y a " + hours + "h";
                long days = hours / 24;
                return "Il y a " + days + " jour(s)";
            }).orElse("Jamais");

            SauvegardeStatsDTO stats = new SauvegardeStatsDTO(totalBackups, totalSize, lastBackup, true);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/lancer")
    public ResponseEntity<?> lancerSauvegarde() {
        try {
            Administrateur admin = getAuthenticatedAdmin();
            SauvegardeBDD sauvegarde = sauvegardeService.lancerSauvegardeManuelle(admin);
            if (sauvegarde != null) {
                SauvegardeDTO dto = convertToDTO(sauvegarde);
                return ResponseEntity.ok(dto);
            } else {
                return ResponseEntity.status(500).body(Map.of("message", "Erreur lors de la création de la sauvegarde"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("message", "Erreur: " + e.getMessage()));
        }
    }

    @PostMapping("/restaurer/{id}")
    public ResponseEntity<?> restaurerSauvegarde(@PathVariable String id) {
        try {
            sauvegardeService.restaurerSauvegarde(id);
            return ResponseEntity.ok(Map.of("message", "Sauvegarde restaurée avec succès", "backupId", id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("message", "Erreur lors de la restauration: " + e.getMessage()));
        }
    }

    @DeleteMapping("/supprimer")
    public ResponseEntity<Map<String, Object>> supprimerSauvegardes(@RequestBody Map<String, List<String>> request) {
        try {
            List<String> ids = request.get("ids");
            if (ids == null || ids.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "Aucun ID fourni"));
            }
            List<SauvegardeBDD> sauvegardes = sauvegardeRepository.findAllById(ids);
            sauvegardeService.supprimerFichiersSauvegardes(sauvegardes);
            sauvegardeRepository.deleteAllById(ids);
            return ResponseEntity.ok(Map.of("message", ids.size() + " sauvegarde(s) supprimée(s) avec succès", "count", ids.size()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("message", "Erreur lors de la suppression: " + e.getMessage()));
        }
    }

    @DeleteMapping("/nettoyer")
    public ResponseEntity<Map<String, Object>> nettoyerSauvegardes(@RequestParam(defaultValue = "30") int joursConservation) {
        try {
            int count = sauvegardeService.nettoyerAnciennesSauvegardes(joursConservation);
            return ResponseEntity.ok(Map.of("message", count + " sauvegarde(s) supprimée(s)", "count", count));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("message", "Erreur lors du nettoyage: " + e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<SauvegardeDTO>> rechercherSauvegardes(@RequestParam String query) {
        try {
            // Utilise la nouvelle méthode que nous avons ajoutée au repository
            List<SauvegardeBDD> resultats = sauvegardeRepository.searchSauvegardes(query);
            List<SauvegardeDTO> dtos = resultats.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    private SauvegardeDTO convertToDTO(SauvegardeBDD sauvegarde) {
        return new SauvegardeDTO(
                sauvegarde.getId(),
                sauvegarde.getDate(),
                sauvegarde.getTime(),
                sauvegarde.getSize(),
                sauvegarde.getCreatedBy().name(),
                sauvegarde.getStatus().name()
        );
    }
}