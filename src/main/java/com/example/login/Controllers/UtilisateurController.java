package com.example.login.Controllers;

import com.example.login.Models.Utilisateur;
import com.example.login.Services.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Utilisateur create(@Valid @RequestBody Utilisateur user,
                              @RequestParam String roleType) {
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

    // --- MÉTHODE "changePassword" MODIFIÉE SANS DTO ---
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