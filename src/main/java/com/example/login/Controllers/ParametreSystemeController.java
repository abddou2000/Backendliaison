package com.example.login.Controllers;

import com.example.login.Models.Administrateur;
import com.example.login.Models.ParametreSysteme;
import com.example.login.Repositories.AdministrateurRepository;
import com.example.login.Services.ParametreSystemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/parametres-systeme")
@CrossOrigin(origins = "*")
public class ParametreSystemeController {

    private static final Logger logger = LoggerFactory.getLogger(ParametreSystemeController.class);

    @Autowired
    private ParametreSystemeService parametreService;

    @Autowired
    private AdministrateurRepository adminRepository;

    /**
     * GET /api/parametres-systeme
     * Récupère les paramètres système
     */
    @GetMapping
    public ResponseEntity<ParametreSysteme> getParametres() {
        logger.info("📥 [GET] /api/parametres-systeme - Début de la récupération des paramètres");
        try {
            ParametreSysteme parametres = parametreService.getParametres();
            logger.info("✅ [GET] /api/parametres-systeme - Paramètres récupérés avec succès");
            return ResponseEntity.ok(parametres);
        } catch (Exception e) {
            logger.error("❌ [GET] /api/parametres-systeme - Erreur lors de la récupération", e);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/parametres-systeme/stats
     * Récupère les statistiques système
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        logger.info("📊 [GET] /api/parametres-systeme/stats - Début de la récupération des statistiques");
        try {
            Map<String, Object> stats = parametreService.getSystemStats();
            logger.info("✅ [GET] /api/parametres-systeme/stats - Statistiques récupérées avec succès");
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            logger.error("❌ [GET] /api/parametres-systeme/stats - Erreur lors de la récupération", e);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * PUT /api/parametres-systeme
     * Met à jour les paramètres système
     */
    @PutMapping
    public ResponseEntity<Map<String, Object>> updateParametres(@RequestBody ParametreSysteme parametres) {
        logger.info("📝 [PUT] /api/parametres-systeme - Début de la mise à jour des paramètres");
        try {
            logger.debug("🔐 Récupération de l'administrateur authentifié...");
            Administrateur admin = getAdminFromAuthentication();
            if (admin == null) {
                logger.warn("⚠️ [PUT] /api/parametres-systeme - Utilisateur non authentifié");
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "Utilisateur non authentifié");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
            }

            logger.info("👤 Administrateur identifié: {}", admin.getUsername());
            ParametreSysteme updated = parametreService.updateParametres(parametres, admin);

            logger.info("✅ [PUT] /api/parametres-systeme - Paramètres mis à jour avec succès");
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Paramètres enregistrés avec succès");
            response.put("data", updated);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("❌ [PUT] /api/parametres-systeme - Erreur lors de l'enregistrement", e);
            e.printStackTrace();
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Erreur lors de l'enregistrement");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * POST /api/parametres-systeme/reset
     * Réinitialise les paramètres aux valeurs par défaut
     */
    @PostMapping("/reset")
    public ResponseEntity<Map<String, Object>> resetParametres() {
        logger.info("🔄 [POST] /api/parametres-systeme/reset - Début de la réinitialisation");
        try {
            logger.debug("🔐 Récupération de l'administrateur authentifié...");
            Administrateur admin = getAdminFromAuthentication();
            if (admin == null) {
                logger.warn("⚠️ [POST] /api/parametres-systeme/reset - Utilisateur non authentifié");
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "Utilisateur non authentifié");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
            }

            logger.info("👤 Administrateur identifié: {}", admin.getUsername());
            ParametreSysteme reset = parametreService.resetParametres(admin);

            logger.info("✅ [POST] /api/parametres-systeme/reset - Paramètres réinitialisés avec succès");
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Paramètres réinitialisés avec succès");
            response.put("data", reset);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("❌ [POST] /api/parametres-systeme/reset - Erreur lors de la réinitialisation", e);
            e.printStackTrace();
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Erreur lors de la réinitialisation");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * ✅ Récupère l'administrateur connecté depuis le contexte de sécurité
     */
    private Administrateur getAdminFromAuthentication() {
        try {
            logger.debug("🔍 Récupération du contexte d'authentification...");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                String username = authentication.getName();
                logger.debug("🔍 Username extrait: {}", username);

                // Chercher l'admin par username (hérite de Utilisateur)
                Administrateur admin = adminRepository.findAll().stream()
                        .filter(a -> {
                            // Vérifier que le username correspond
                            return a.getUsername() != null && a.getUsername().equals(username);
                        })
                        .findFirst()
                        .orElse(null);

                if (admin != null) {
                    logger.debug("✅ Administrateur trouvé: {}", admin.getUsername());
                } else {
                    logger.warn("⚠️ Aucun administrateur trouvé avec le username: {}", username);
                }

                return admin;
            }
            logger.warn("⚠️ Aucune authentification valide trouvée");
            return null;
        } catch (Exception e) {
            logger.error("❌ Erreur lors de la récupération de l'administrateur authentifié", e);
            e.printStackTrace();
            return null;
        }
    }
}