package com.example.login.Controllers;

import com.example.login.Models.SauvegardeBDD;
import com.example.login.Services.SauvegardeBDDService;
import lombok.Data; // <-- Importation Lombok
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sauvegardes")
@CrossOrigin(origins = "*")
public class SauvegardeBDDController {

    private final SauvegardeBDDService service;

    public SauvegardeBDDController(SauvegardeBDDService service) {
        this.service = service;
    }

    // DTO interne pour recevoir le mot de passe
    @Data
    static class PasswordRequest {
        private String password;
    }

    @GetMapping
    public ResponseEntity<List<SauvegardeBDD>> getAllSauvegardes() {
        return ResponseEntity.ok(service.getAllSauvegardes());
    }

    @PostMapping("/lancer")
    public ResponseEntity<SauvegardeBDD> lancerSauvegardeManuelle(@AuthenticationPrincipal UserDetails userDetails) {
        // Note: Cette logique devra être améliorée pour extraire l'ID de userDetails
        Long adminId = 1L;
        SauvegardeBDD nouvelleSauvegarde = service.creerSauvegardeManuelle(adminId);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouvelleSauvegarde);
    }

    /**
     * MODIFIÉ : Supprime toutes les archives après validation du mot de passe de l'admin connecté.
     */
    @DeleteMapping("/nettoyer")
    public ResponseEntity<Void> nettoyerToutesLesArchives(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody PasswordRequest request) {

        // On récupère le username de l'admin actuellement connecté via le token JWT
        String adminUsername = userDetails.getUsername();

        // On passe le username et le mot de passe au service pour validation
        service.nettoyerToutesLesArchives(adminUsername, request.getPassword());

        return ResponseEntity.noContent().build();
    }
}