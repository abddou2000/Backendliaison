package com.example.login.controllers;

import com.example.login.models.TypeAttestation;
import com.example.login.Services.TypeAttestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/types-attestation")
@CrossOrigin(origins = "*")
public class TypeAttestationController {

    private final TypeAttestationService service;

    @Autowired
    public TypeAttestationController(TypeAttestationService service) {
        this.service = service;
    }

    // ➕ Créer
    @PostMapping
    public ResponseEntity<TypeAttestation> create(@RequestBody TypeAttestation t) {
        return ResponseEntity.status(201).body(service.create(t));
    }

    // 📄 Lister tous
    @GetMapping
    public ResponseEntity<List<TypeAttestation>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    // 🔍 Obtenir par ID
    @GetMapping("/{id}")
    public ResponseEntity<TypeAttestation> getById(@PathVariable String id) {
        TypeAttestation t = service.getById(id);
        return t == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(t);
    }

    // 🔍 Rechercher par nom
    @GetMapping("/search")
    public ResponseEntity<TypeAttestation> findByName(@RequestParam String nom) {
        TypeAttestation t = service.findByName(nom);
        return t == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(t);
    }


    @PutMapping("/{id}")
    public ResponseEntity<TypeAttestation> update(
            @PathVariable String id,
            @RequestBody TypeAttestation details) {
        TypeAttestation updated = service.update(id, details);
        return updated == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
