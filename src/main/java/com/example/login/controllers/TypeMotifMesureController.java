package com.example.login.controllers;

import com.example.login.models.TypeMotifMesure;
import com.example.login.Services.TypeMotifMesureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/types-motif-mesure")
@CrossOrigin(origins = "*")
public class TypeMotifMesureController {

    private final TypeMotifMesureService service;

    @Autowired
    public TypeMotifMesureController(TypeMotifMesureService service) {
        this.service = service;
    }

    // ➕ Créer
    @PostMapping
    public ResponseEntity<TypeMotifMesure> create(@RequestBody TypeMotifMesure t) {
        return ResponseEntity.status(201).body(service.create(t));
    }

    // 📄 Lister toutes
    @GetMapping
    public ResponseEntity<List<TypeMotifMesure>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    // 🔍 Par ID
    @GetMapping("/{id}")
    public ResponseEntity<TypeMotifMesure> getById(@PathVariable String id) {
        TypeMotifMesure t = service.getById(id);
        return t == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(t);
    }

    // ✏️ Mettre à jour
    @PutMapping("/{id}")
    public ResponseEntity<TypeMotifMesure> update(
            @PathVariable String id,
            @RequestBody TypeMotifMesure details) {
        TypeMotifMesure updated = service.update(id, details);
        return updated == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(updated);
    }

    // ❌ Supprimer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // 🔍 Recherche par code
    @GetMapping("/search/code")
    public ResponseEntity<List<TypeMotifMesure>> byCode(@RequestParam String code) {
        return ResponseEntity.ok(service.findByCode(code));
    }

    // 🔍 Recherche par libellé
    @GetMapping("/search/libelle")
    public ResponseEntity<List<TypeMotifMesure>> byLibelle(@RequestParam String libelle) {
        return ResponseEntity.ok(service.findByLibelle(libelle));
    }
}
