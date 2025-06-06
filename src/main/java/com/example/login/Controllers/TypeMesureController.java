package com.example.login.Controllers;

import com.example.login.Models.TypeMesure;
import com.example.login.Services.TypeMesureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/types-mesure")
@CrossOrigin(origins = "*")
public class TypeMesureController {

    private final TypeMesureService service;

    @Autowired
    public TypeMesureController(TypeMesureService service) {
        this.service = service;
    }

    // ➕ Créer
    @PostMapping
    public ResponseEntity<TypeMesure> create(@RequestBody TypeMesure t) {
        return ResponseEntity.status(201).body(service.create(t));
    }

    // 📄 Lister toutes
    @GetMapping
    public ResponseEntity<List<TypeMesure>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    // 🔍 Par ID
    @GetMapping("/{id}")
    public ResponseEntity<TypeMesure> getById(@PathVariable String id) {
        TypeMesure t = service.getById(id);
        return t == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(t);
    }

    // ✏️ Mettre à jour
    @PutMapping("/{id}")
    public ResponseEntity<TypeMesure> update(
            @PathVariable String id,
            @RequestBody TypeMesure details) {
        TypeMesure updated = service.update(id, details);
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
    public ResponseEntity<List<TypeMesure>> byCode(@RequestParam String code) {
        return ResponseEntity.ok(service.findByCode(code));
    }

    // 🔍 Recherche par nom
    @GetMapping("/search/nom")
    public ResponseEntity<List<TypeMesure>> byNom(@RequestParam String nom) {
        return ResponseEntity.ok(service.findByNom(nom));
    }

    // 🔍 Recherche embauche true/false
    @GetMapping("/search/embauche")
    public ResponseEntity<List<TypeMesure>> byEmbauche(@RequestParam Boolean embauche) {
        return ResponseEntity.ok(service.findByEmbauche(embauche));
    }
}
