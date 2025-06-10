package com.example.login.controllers;

import com.example.login.models.SauvegardeBDD;
import com.example.login.Services.SauvegardeBDDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sauvegardes")
@CrossOrigin(origins = "*")
public class SauvegardeBDDController {

    private final SauvegardeBDDService service;

    @Autowired
    public SauvegardeBDDController(SauvegardeBDDService service) {
        this.service = service;
    }

    // ➕ Créer une sauvegarde
    @PostMapping
    public ResponseEntity<SauvegardeBDD> create(@RequestBody SauvegardeBDD s) {
        SauvegardeBDD saved = service.create(s);
        return ResponseEntity.status(201).body(saved);
    }

    // 📄 Lister toutes les sauvegardes
    @GetMapping
    public ResponseEntity<List<SauvegardeBDD>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    // 🔍 Obtenir par ID
    @GetMapping("/{id}")
    public ResponseEntity<SauvegardeBDD> getById(@PathVariable String id) {
        SauvegardeBDD s = service.getById(id);
        if (s == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(s);
    }

    // 🔍 Lister par administrateur
    @GetMapping("/search/admin")
    public ResponseEntity<List<SauvegardeBDD>> byAdmin(@RequestParam String idAdmin) {
        return ResponseEntity.ok(service.listByAdmin(idAdmin));
    }

    // ✏️ Mettre à jour
    @PutMapping("/{id}")
    public ResponseEntity<SauvegardeBDD> update(
            @PathVariable String id,
            @RequestBody SauvegardeBDD details) {
        SauvegardeBDD updated = service.update(id, details);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    // ❌ Supprimer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!service.delete(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}
