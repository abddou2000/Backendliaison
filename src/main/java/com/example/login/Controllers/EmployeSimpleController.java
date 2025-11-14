package com.example.login.Controllers;

import com.example.login.Models.ActivityLog;
import com.example.login.Models.EmployeSimple;
import com.example.login.Models.Utilisateur;
import com.example.login.Services.ActivityLogService;
import com.example.login.Services.EmployeSimpleService;
import com.example.login.Services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/employes")
@CrossOrigin(origins = "*")
public class EmployeSimpleController {

    @Autowired
    private EmployeSimpleService employeSimpleService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ActivityLogService activityLogService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('RH')")
    public ResponseEntity<Page<EmployeSimple>> getAllEmployes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        try {
            Sort sort = sortDirection.equalsIgnoreCase("desc")
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<EmployeSimple> employes = employeSimpleService.findAll(pageable);

            // Hydrater les caches pour chaque employé
            employes.getContent().forEach(e -> e.hydrateCache());

            return ResponseEntity.ok(employes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RH')")
    public ResponseEntity<List<EmployeSimple>> getAllEmployesList() {
        try {
            List<EmployeSimple> employes = employeSimpleService.findAll();

            // Hydrater les caches pour chaque employé
            employes.forEach(e -> e.hydrateCache());

            return ResponseEntity.ok(employes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RH') or (hasRole('EMPLOYE') and #id == authentication.principal.id)")
    public ResponseEntity<EmployeSimple> getEmployeById(@PathVariable Long id) {
        try {
            Optional<EmployeSimple> employe = employeSimpleService.findById(id);
            if (employe.isPresent()) {
                employe.get().hydrateCache();
                return ResponseEntity.ok(employe.get());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/matricule/{matricule}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RH')")
    public ResponseEntity<EmployeSimple> getEmployeByMatricule(@PathVariable String matricule) {
        try {
            Optional<EmployeSimple> employe = employeSimpleService.findByMatricule(matricule);
            if (employe.isPresent()) {
                employe.get().hydrateCache();
                return ResponseEntity.ok(employe.get());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Création d'un profil employé
     * Le frontend doit envoyer : { utilisateurId: 123, ... }
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('RH')")
    public ResponseEntity<?> createEmploye(@RequestBody Map<String, Object> requestData, Authentication auth) {
        try {
            System.out.println("📥 Données reçues: " + requestData);

            // 1️⃣ Récupérer l'ID utilisateur
            Long userId = null;

            if (requestData.containsKey("utilisateurId")) {
                Object userIdObj = requestData.get("utilisateurId");
                userId = userIdObj instanceof Number ? ((Number) userIdObj).longValue() : Long.parseLong(userIdObj.toString());
            } else if (requestData.containsKey("utilisateur")) {
                Object utilisateurObj = requestData.get("utilisateur");
                if (utilisateurObj instanceof Map) {
                    Map<?, ?> utilisateurMap = (Map<?, ?>) utilisateurObj;
                    if (utilisateurMap.containsKey("id")) {
                        Object idObj = utilisateurMap.get("id");
                        userId = idObj instanceof Number ? ((Number) idObj).longValue() : Long.parseLong(idObj.toString());
                    }
                }
            }

            if (userId == null) {
                System.err.println("❌ Aucun utilisateurId trouvé dans la requête");
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "utilisateurId est requis"));
            }

            System.out.println("🔍 ID utilisateur extrait: " + userId);

            // 2️⃣ Vérifier si un profil employé existe déjà pour cet utilisateur
            if (employeSimpleService.existsByUtilisateurId(userId)) {
                System.err.println("⚠️ Un profil employé existe déjà pour l'utilisateur " + userId);
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("error", "Un profil employé existe déjà pour cet utilisateur"));
            }

            // 3️⃣ Récupérer l'utilisateur MANAGED depuis la base de données
            Utilisateur utilisateurManaged;
            try {
                utilisateurManaged = utilisateurService.getById(userId);
            } catch (RuntimeException e) {
                System.err.println("❌ Utilisateur non trouvé: " + userId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Utilisateur non trouvé avec l'ID: " + userId));
            }

            System.out.println("✅ Utilisateur trouvé: " + utilisateurManaged.getUsername());
            System.out.println("🔑 Utilisateur ID: " + utilisateurManaged.getId());

            // 4️⃣ Créer l'objet EmployeSimple et hydrater les champs
            EmployeSimple employe = new EmployeSimple();

            // 🔥 CRUCIAL : Définir explicitement l'utilisateur ET l'ID
            employe.setUtilisateur(utilisateurManaged);
            employe.setId(utilisateurManaged.getId());

            System.out.println("🆔 ID assigné à l'employé: " + employe.getId());
            System.out.println("👤 Utilisateur assigné: " + employe.getUtilisateur().getUsername());

            // Mapper tous les champs optionnels du JSON
            if (requestData.containsKey("adresse")) employe.setAdresse((String) requestData.get("adresse"));
            if (requestData.containsKey("telephone")) employe.setTelephone((String) requestData.get("telephone"));
            if (requestData.containsKey("posteOccupe")) employe.setPosteOccupe((String) requestData.get("posteOccupe"));
            if (requestData.containsKey("dateNaissance")) employe.setDateNaissance(parseLocalDate(requestData.get("dateNaissance")));
            if (requestData.containsKey("genre")) employe.setGenre(EmployeSimple.Genre.valueOf((String) requestData.get("genre")));
            if (requestData.containsKey("situationFamiliale")) employe.setSituationFamiliale((String) requestData.get("situationFamiliale"));
            if (requestData.containsKey("enfantsACharge")) employe.setEnfantsACharge(((Number) requestData.get("enfantsACharge")).intValue());
            if (requestData.containsKey("departement")) employe.setDepartement((String) requestData.get("departement"));
            if (requestData.containsKey("typeContrat")) employe.setTypeContrat((String) requestData.get("typeContrat"));
            if (requestData.containsKey("lieuNaissance")) employe.setLieuNaissance((String) requestData.get("lieuNaissance"));
            if (requestData.containsKey("nationalite")) employe.setNationalite((String) requestData.get("nationalite"));
            if (requestData.containsKey("ville")) employe.setVille((String) requestData.get("ville"));
            if (requestData.containsKey("codePostal")) employe.setCodePostal((String) requestData.get("codePostal"));
            if (requestData.containsKey("emailPersonnel")) employe.setEmailPersonnel((String) requestData.get("emailPersonnel"));
            if (requestData.containsKey("numeroSecuriteSociale")) employe.setNumeroSecuriteSociale((String) requestData.get("numeroSecuriteSociale"));
            if (requestData.containsKey("dateEmbauche")) employe.setDateEmbauche(parseLocalDate(requestData.get("dateEmbauche")));
            if (requestData.containsKey("manager")) employe.setManager((String) requestData.get("manager"));
            if (requestData.containsKey("equipe")) employe.setEquipe((String) requestData.get("equipe"));
            if (requestData.containsKey("telephonePro")) employe.setTelephonePro((String) requestData.get("telephonePro"));
            if (requestData.containsKey("formation")) employe.setFormation((String) requestData.get("formation"));
            if (requestData.containsKey("experience")) employe.setExperience((String) requestData.get("experience"));
            if (requestData.containsKey("photoProfil")) employe.setPhotoProfil((String) requestData.get("photoProfil"));
            if (requestData.containsKey("statut")) employe.setStatut((String) requestData.get("statut"));

            System.out.println("📝 Données employé hydratées");
            System.out.println("🔍 État avant save() - ID: " + employe.getId() + ", Utilisateur: " + (employe.getUtilisateur() != null ? employe.getUtilisateur().getUsername() : "null"));

            // 5️⃣ Enregistrement en base
            EmployeSimple saved = employeSimpleService.save(employe);

            // Hydrater le cache pour le retour JSON
            saved.hydrateCache();

            System.out.println("✅ Profil employé créé avec succès pour: " + utilisateurManaged.getUsername());

            // 6️⃣ Logger l'activité
            logActivity("Création profil employé", ActivityLog.ActivityType.CREATION,
                    ActivityLog.ActivityStatus.SUCCESS, utilisateurManaged.getUsername(), auth);

            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la création du profil employé: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erreur interne: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RH')")
    public ResponseEntity<EmployeSimple> updateEmploye(@PathVariable Long id, @RequestBody EmployeSimple details, Authentication auth) {
        try {
            Optional<EmployeSimple> opt = employeSimpleService.findById(id);
            if (opt.isEmpty()) return ResponseEntity.notFound().build();

            EmployeSimple employe = opt.get();
            if (details.getAdresse() != null) employe.setAdresse(details.getAdresse());
            if (details.getTelephone() != null) employe.setTelephone(details.getTelephone());
            if (details.getPosteOccupe() != null) employe.setPosteOccupe(details.getPosteOccupe());
            if (details.getDateNaissance() != null) employe.setDateNaissance(details.getDateNaissance());
            if (details.getGenre() != null) employe.setGenre(details.getGenre());
            if (details.getSituationFamiliale() != null) employe.setSituationFamiliale(details.getSituationFamiliale());
            if (details.getEnfantsACharge() != null) employe.setEnfantsACharge(details.getEnfantsACharge());
            if (details.getDepartement() != null) employe.setDepartement(details.getDepartement());
            if (details.getTypeContrat() != null) employe.setTypeContrat(details.getTypeContrat());
            if (details.getLieuNaissance() != null) employe.setLieuNaissance(details.getLieuNaissance());
            if (details.getNationalite() != null) employe.setNationalite(details.getNationalite());
            if (details.getVille() != null) employe.setVille(details.getVille());
            if (details.getCodePostal() != null) employe.setCodePostal(details.getCodePostal());
            if (details.getEmailPersonnel() != null) employe.setEmailPersonnel(details.getEmailPersonnel());
            if (details.getNumeroSecuriteSociale() != null) employe.setNumeroSecuriteSociale(details.getNumeroSecuriteSociale());
            if (details.getDateEmbauche() != null) employe.setDateEmbauche(details.getDateEmbauche());
            if (details.getManager() != null) employe.setManager(details.getManager());
            if (details.getEquipe() != null) employe.setEquipe(details.getEquipe());
            if (details.getTelephonePro() != null) employe.setTelephonePro(details.getTelephonePro());
            if (details.getFormation() != null) employe.setFormation(details.getFormation());
            if (details.getExperience() != null) employe.setExperience(details.getExperience());
            if (details.getPhotoProfil() != null) employe.setPhotoProfil(details.getPhotoProfil());
            if (details.getStatut() != null) employe.setStatut(details.getStatut());

            EmployeSimple updated = employeSimpleService.save(employe);
            updated.hydrateCache();

            logActivity("Modification profil employé", ActivityLog.ActivityType.MODIFICATION,
                    ActivityLog.ActivityStatus.SUCCESS, employe.getUtilisateur().getUsername(), auth);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEmploye(@PathVariable Long id, Authentication auth) {
        try {
            if (!employeSimpleService.existsById(id)) return ResponseEntity.notFound().build();
            Optional<EmployeSimple> employe = employeSimpleService.findById(id);
            String username = employe.map(e -> e.getUtilisateur().getUsername()).orElse("Inconnu");
            employeSimpleService.deleteById(id);
            logActivity("Suppression profil employé", ActivityLog.ActivityType.SUPPRESSION,
                    ActivityLog.ActivityStatus.WARNING, username, auth);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/departement/{departement}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RH')")
    public ResponseEntity<List<EmployeSimple>> getEmployesByDepartement(@PathVariable String departement) {
        List<EmployeSimple> employes = employeSimpleService.findByDepartement(departement);
        employes.forEach(e -> e.hydrateCache());
        return ResponseEntity.ok(employes);
    }

    @GetMapping("/poste/{poste}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RH')")
    public ResponseEntity<List<EmployeSimple>> getEmployesByPoste(@PathVariable String poste) {
        List<EmployeSimple> employes = employeSimpleService.findByPosteOccupe(poste);
        employes.forEach(e -> e.hydrateCache());
        return ResponseEntity.ok(employes);
    }

    @GetMapping("/mon-profil")
    @PreAuthorize("hasRole('EMPLOYE') or hasRole('ADMIN') or hasRole('RH')")
    public ResponseEntity<EmployeSimple> getMonProfil(Authentication auth) {
        try {
            Utilisateur utilisateur = (Utilisateur) auth.getPrincipal();
            Optional<EmployeSimple> employe = employeSimpleService.findByUtilisateurId(utilisateur.getId());
            if (employe.isPresent()) {
                employe.get().hydrateCache();
                return ResponseEntity.ok(employe.get());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void logActivity(String action, ActivityLog.ActivityType type, ActivityLog.ActivityStatus status, String target, Authentication auth) {
        try {
            Utilisateur utilisateur = (Utilisateur) auth.getPrincipal();
            ActivityLog log = new ActivityLog();
            log.setTimestamp(LocalDateTime.now());
            log.setUserId(utilisateur.getId());
            log.setUserName(utilisateur.getPrenom() + " " + utilisateur.getNom());
            log.setUserEmail(utilisateur.getEmail());
            log.setAction(action);
            log.setType(type);
            log.setStatus(status);
            log.setTarget(target);
            log.setDetails(action + " - Cible: " + target);
            activityLogService.create(log);
        } catch (Exception e) {
            System.err.println("⚠️ Erreur log: " + e.getMessage());
        }
    }

    private java.time.LocalDate parseLocalDate(Object dateObj) {
        if (dateObj == null) return null;
        if (dateObj instanceof String) {
            return java.time.LocalDate.parse((String) dateObj);
        }
        return null;
    }
}