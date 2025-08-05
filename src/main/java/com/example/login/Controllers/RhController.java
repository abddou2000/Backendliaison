package com.example.login.Controllers;

import com.example.login.Models.Rh;
import com.example.login.Services.RhService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rh")
@CrossOrigin(origins = "*")
public class RhController {

    private final RhService service;

    public RhController(RhService service) {
        this.service = service;
    }

    /**
     * Crée un nouveau profil RH pour un utilisateur existant.
     * C'est la 2ème étape du processus de création.
     */
    @PostMapping
    public ResponseEntity<Rh> create(@RequestBody Rh rhData) {
        Rh createdRh = service.create(rhData);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRh);
    }

    /**
     * Récupère la liste de tous les profils RH.
     * Nécessite des droits d'administrateur.
     */
    @GetMapping
    public ResponseEntity<List<Rh>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    /**
     * Récupère un profil RH par son ID (qui est l'ID de l'utilisateur).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Rh> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Supprime un utilisateur et son profil RH associé.
     * Nécessite des droits d'administrateur.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}