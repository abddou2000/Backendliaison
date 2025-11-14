    package com.example.login.Controllers;

    import com.example.login.Models.Utilisateur;
    import com.example.login.Models.Role;
    import com.example.login.Models.AffectationRoleUtilisateur;
    import com.example.login.Repositories.RoleRepository;
    import com.example.login.Repositories.AffectationRoleUtilisateurRepository;
    import com.example.login.Services.UtilisateurService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Map;

    @RestController
    @RequestMapping("/api/utilisateurs")
    @CrossOrigin(origins = "*")
    public class UtilisateurController {

        private final UtilisateurService service;

        @Autowired
        private RoleRepository roleRepo;

        @Autowired
        private AffectationRoleUtilisateurRepository aruRepo;

        public UtilisateurController(UtilisateurService service) {
            this.service = service;
        }

        // ✅ Création d’un utilisateur avec rôle principal + insertion dans la table d’affectation
        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public Utilisateur create(@RequestBody Map<String, Object> requestData,
                                  @RequestParam String roleType) {

            Utilisateur user = new Utilisateur();

            // ⚙️ Hydratation des champs reçus depuis le front
            user.setUsername((String) requestData.get("username"));
            user.setPassword((String) requestData.get("password")); // champ transient
            user.setNom((String) requestData.get("nom"));
            user.setPrenom((String) requestData.get("prenom"));
            user.setEmail((String) requestData.get("email"));
            user.setMatricule((String) requestData.get("matricule"));

            // ✅ Étape 1 : Création du user avec son rôle principal
            Utilisateur createdUser = service.create(user, roleType);

            // ✅ Étape 2 : Ajouter automatiquement ce rôle principal dans la table d’affectation
            try {
                roleRepo.findByType(roleType).ifPresentOrElse(role -> {
                    AffectationRoleUtilisateur link = new AffectationRoleUtilisateur(
                            new AffectationRoleUtilisateur.Id(createdUser.getId(), role.getId()),
                            createdUser,
                            role
                    );
                    aruRepo.save(link);
                    System.out.println("✅ Lien d’affectation créé : utilisateur " + createdUser.getUsername()
                            + " → rôle " + roleType);
                }, () -> {
                    System.err.println("⚠️ Aucun rôle trouvé pour le type : " + roleType);
                });
            } catch (Exception e) {
                System.err.println("⚠️ Erreur lors de l’ajout du rôle principal dans AffectationRoleUtilisateur : " + e.getMessage());
            }

            return createdUser;
        }

        // 🔹 Lister tous les utilisateurs
        @GetMapping
        public List<Utilisateur> listAll() {
            return service.getAll();
        }

        // 🔹 Récupérer un utilisateur par ID
        @GetMapping("/{id}")
        public Utilisateur getById(@PathVariable Long id) {
            return service.getById(id);
        }

        // 🔹 Récupérer les utilisateurs d’un rôle spécifique
        @GetMapping("/role/{roleType}")
        public List<Utilisateur> getByRole(@PathVariable String roleType) {
            return service.getByRole(roleType);
        }

        // 🔹 Changer le mot de passe d’un utilisateur
        @PutMapping("/{id}/password")
        public ResponseEntity<Void> changePassword(
                @PathVariable Long id,
                @RequestParam String ancienMdp,
                @RequestParam String nouveauMdp,
                @RequestParam String confirmationMdp) {

            service.updatePassword(id, ancienMdp, nouveauMdp, confirmationMdp);
            return ResponseEntity.noContent().build();
        }

        // 🔹 Mettre à jour les infos utilisateur
        @PutMapping("/{id}")
        public ResponseEntity<Utilisateur> updateUtilisateurDetails(
                @PathVariable Long id,
                @RequestBody Map<String, Object> requestData) {

            String nom = (String) requestData.get("nom");
            String prenom = (String) requestData.get("prenom");
            String email = (String) requestData.get("email");
            String username = (String) requestData.get("username");
            String matricule = (String) requestData.get("matricule");
            Boolean actif = (Boolean) requestData.get("actif");

            try {
                Utilisateur updatedUser = service.updateUtilisateurDetails(id, nom, prenom, email, username, matricule, actif);
                return ResponseEntity.ok(updatedUser);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
            }
        }

        // 🔹 Réinitialisation du mot de passe par un admin
        @PutMapping("/{id}/admin-reset-password")
        public ResponseEntity<Void> adminResetPassword(
                @PathVariable String id,
                @RequestBody Map<String, String> requestData) {

            String nouveauMdp = requestData.get("nouveauMdp");

            if (nouveauMdp == null || nouveauMdp.trim().isEmpty()) {
                System.err.println("❌ Mot de passe vide ou null");
                return ResponseEntity.badRequest().build();
            }

            try {
                Long userId = Long.parseLong(id.trim());

                if (userId <= 0) {
                    System.err.println("❌ ID invalide (doit être positif): " + userId);
                    return ResponseEntity.badRequest().build();
                }

                System.out.println("🔍 [Admin Reset Password]");
                service.adminResetPassword(userId, nouveauMdp);
                System.out.println("✅ Mot de passe réinitialisé avec succès pour l'ID: " + userId);

                return ResponseEntity.noContent().build();

            } catch (NumberFormatException e) {
                System.err.println("❌ [NumberFormatException] ID invalide: '" + id + "'");
                return ResponseEntity.badRequest().build();
            } catch (RuntimeException e) {
                System.err.println("❌ [RuntimeException] Erreur lors de la réinitialisation : " + e.getMessage());
                if (e.getMessage() != null && e.getMessage().contains("non trouvé")) {
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        // 🔹 Supprimer un utilisateur
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
            try {
                service.deleteUtilisateur(id);
                return ResponseEntity.noContent().build();
            } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
            }
        }
    }
