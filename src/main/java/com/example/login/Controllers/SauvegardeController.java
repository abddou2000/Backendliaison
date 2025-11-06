package com.example.login.Controllers;

import com.example.login.Models.Administrateur;
import com.example.login.Models.SauvegardeBDD;
import com.example.login.Repositories.AdministrateurRepository;
import com.example.login.Services.SauvegardeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur REST pour gérer les opérations liées aux sauvegardes de la base de données.
 * C'est le point d'entrée pour toutes les requêtes du frontend.
 */
@RestController
@RequestMapping("/api/sauvegardes")
public class SauvegardeController {

    @Autowired
    private SauvegardeService sauvegardeService;

    @Autowired
    private AdministrateurRepository adminRepository;

    /**
     * Endpoint pour lister toutes les sauvegardes.
     * Correspond à `api.get('')`.
     */
    @GetMapping
    public ResponseEntity<List<SauvegardeBDD>> listerSauvegardes() {
        return ResponseEntity.ok(sauvegardeService.findAll());
    }

    /**
     * Endpoint pour obtenir les statistiques du tableau de bord.
     * Correspond à `api.get('/stats')`.
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        return ResponseEntity.ok(sauvegardeService.getDashboardStats());
    }

    /**
     * Endpoint pour rechercher des sauvegardes.
     * Correspond à `api.get('/search', ...)`.
     */
    @GetMapping("/search")
    public ResponseEntity<List<SauvegardeBDD>> rechercherSauvegardes(
            @RequestParam(name = "query", required = false, defaultValue = "") String query) {
        if (query.isBlank()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(sauvegardeService.search(query.replace("-", "")));
    }

    /**
     * Endpoint pour lancer une sauvegarde manuelle.
     * Correspond à `api.post('/lancer')`.
     */
    @PostMapping("/lancer")
    public ResponseEntity<SauvegardeBDD> lancerSauvegardeManuelle(
            @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            // Récupérer un administrateur depuis la base de données
            Administrateur admin = adminRepository.findAll().stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Aucun administrateur trouvé"));

            // Lancer la sauvegarde
            SauvegardeBDD nouvelleSauvegarde = sauvegardeService.lancerSauvegardeManuelle(admin);

            // Vérifier si le service retourne null (sauvegarde asynchrone)
            if (nouvelleSauvegarde == null) {
                return ResponseEntity.accepted().build();
            }

            return ResponseEntity.accepted().body(nouvelleSauvegarde);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint pour supprimer plusieurs sauvegardes en une seule requête.
     * Correspond à `api.delete('/supprimer', ...)`.
     */
    @DeleteMapping("/supprimer")
    public ResponseEntity<Void> supprimerSauvegardesEnMasse(
            @RequestBody Map<String, List<String>> payload) {
        List<String> ids = payload.get("ids");
        if (ids != null && !ids.isEmpty()) {
            sauvegardeService.deleteByIds(ids);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint pour restaurer une sauvegarde.
     * Correspondra à la fonction handleRestore(id) du frontend.
     */
    @PostMapping("/{id}/restaurer")
    public ResponseEntity<Map<String, String>> restaurerSauvegarde(@PathVariable String id) {
        try {
            sauvegardeService.restaurerSauvegarde(id);
            return ResponseEntity.ok(Map.of("message", "La restauration a démarré avec succès."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Échec de la restauration : " + e.getMessage()));
        }
    }

    /**
     * Endpoint pour télécharger le fichier d'une sauvegarde.
     * Correspondra à la fonction de téléchargement du frontend.
     */
    @GetMapping("/{id}/telecharger")
    public ResponseEntity<Resource> telechargerFichierSauvegarde(@PathVariable String id) {
        try {
            Resource resource = sauvegardeService.chargerFichierEnTantQueResource(id);
            String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}