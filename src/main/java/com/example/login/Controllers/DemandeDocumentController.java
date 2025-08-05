package com.example.login.Controllers;

import com.example.login.Models.DemandeDocument;
import com.example.login.Services.DemandeDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demandes-documents")
@CrossOrigin(origins = "*")
public class DemandeDocumentController {

    @Autowired
    private DemandeDocumentService service;

    // CREATE
    @PostMapping
    public ResponseEntity<DemandeDocument> create(@RequestBody DemandeDocument demande) {
        return ResponseEntity.ok(service.create(demande));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<DemandeDocument>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<DemandeDocument> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CORRECTED METHOD: Changed @PathVariable to Long
    // READ BY EMPLOYE
    @GetMapping("/employe/{id_user}")
    public ResponseEntity<List<DemandeDocument>> getByEmploye(@PathVariable Long id_user) {
        return ResponseEntity.ok(service.getByEmploye(id_user));
    }

    // READ BY TYPE
    @GetMapping("/type/{type}")
    public ResponseEntity<List<DemandeDocument>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(service.getByType(type));
    }

    // READ BY ETAT
    @GetMapping("/etat/{etat}")
    public ResponseEntity<List<DemandeDocument>> getByEtat(@PathVariable String etat) {
        return ResponseEntity.ok(service.getByEtat(etat));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<DemandeDocument> update(@PathVariable String id, @RequestBody DemandeDocument updated) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.update(id, updated));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}