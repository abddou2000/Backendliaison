package com.example.login.Controllers;

import com.example.login.Models.Utilisateur;
import com.example.login.Services.UtilisateurService;
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

    public UtilisateurController(UtilisateurService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Utilisateur create(@RequestBody Map<String, Object> requestData,
                              @RequestParam String roleType) {

        Utilisateur user = new Utilisateur();

        user.setUsername((String) requestData.get("username"));
        user.setPassword((String) requestData.get("password"));
        user.setNom((String) requestData.get("nom"));
        user.setPrenom((String) requestData.get("prenom"));
        user.setEmail((String) requestData.get("email"));
        user.setMatricule((String) requestData.get("matricule"));

        return service.create(user, roleType);
    }

    @GetMapping
    public List<Utilisateur> listAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Utilisateur getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/role/{roleType}")
    public List<Utilisateur> getByRole(@PathVariable String roleType) {
        return service.getByRole(roleType);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @RequestParam String ancienMdp,
            @RequestParam String nouveauMdp,
            @RequestParam String confirmationMdp) {

        service.updatePassword(id, ancienMdp, nouveauMdp, confirmationMdp);
        return ResponseEntity.noContent().build();
    }

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

    @PutMapping("/{id}/admin-reset-password")
    public ResponseEntity<Void> adminResetPassword(
            @PathVariable Long id,
            @RequestBody Map<String, String> requestData) {

        String nouveauMdp = requestData.get("nouveauMdp");

        if (nouveauMdp == null || nouveauMdp.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            service.adminResetPassword(id, nouveauMdp);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

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