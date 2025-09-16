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

        // CRÉATION MANUELLE de l'objet Utilisateur depuis la Map
        Utilisateur user = new Utilisateur();

        // Récupération des données depuis la Map JSON
        user.setUsername((String) requestData.get("username"));
        user.setPassword((String) requestData.get("password"));
        user.setNom((String) requestData.get("nom"));
        user.setPrenom((String) requestData.get("prenom"));
        user.setEmail((String) requestData.get("email"));

        // LOGS pour vérifier
        System.out.println("=== DONNÉES REÇUES ===");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Nom: " + user.getNom());
        System.out.println("Prenom: " + user.getPrenom());
        System.out.println("Password: " + (user.getPassword() != null ? "FOURNI" : "NULL"));
        System.out.println("=====================");

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
}