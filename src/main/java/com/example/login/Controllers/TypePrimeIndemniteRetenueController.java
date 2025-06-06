package com.example.login.Controllers;

import com.example.login.Models.TypePrimeIndemniteRetenue;
import com.example.login.Services.TypePrimeIndemniteRetenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/types-prime")
@CrossOrigin(origins = "*")
public class TypePrimeIndemniteRetenueController {

    private final TypePrimeIndemniteRetenueService service;

    @Autowired
    public TypePrimeIndemniteRetenueController(TypePrimeIndemniteRetenueService service) {
        this.service = service;
    }

    // ➕ Créer
    @PostMapping
    public ResponseEntity<TypePrimeIndemniteRetenue> create(@RequestBody TypePrimeIndemniteRetenue t) {
        return ResponseEntity.status(201).body(service.create(t));
    }

    // 📄 Lister toutes
    @GetMapping
    public ResponseEntity<List<TypePrimeIndemniteRetenue>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    // 🔍 Par ID
    @GetMapping("/{id}")
    public ResponseEntity<TypePrimeIndemniteRetenue> getById(@PathVariable String id) {
        TypePrimeIndemniteRetenue t = service.getById(id);
        return t == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(t);
    }

    // ✏️ Mettre à jour
    @PutMapping("/{id}")
    public ResponseEntity<TypePrimeIndemniteRetenue> update(
            @PathVariable String id,
            @RequestBody TypePrimeIndemniteRetenue details) {
        TypePrimeIndemniteRetenue updated = service.update(id, details);
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

    // 🔍 Recherche par type
    @GetMapping("/search/type")
    public ResponseEntity<List<TypePrimeIndemniteRetenue>> byType(@RequestParam String type) {
        return ResponseEntity.ok(service.findByType(type));
    }

    // 🔍 Recherche par unité
    @GetMapping("/search/unite")
    public ResponseEntity<List<TypePrimeIndemniteRetenue>> byUnite(@RequestParam String unite) {
        return ResponseEntity.ok(service.findByUnite(unite));
    }

    // 🔍 Recherche soumisCNSS
    @GetMapping("/search/soumis-cnss")
    public ResponseEntity<List<TypePrimeIndemniteRetenue>> bySoumisCNSS(@RequestParam Boolean flag) {
        return ResponseEntity.ok(service.findBySoumisCNSS(flag));
    }

    // 🔍 Recherche soumisAMO
    @GetMapping("/search/soumis-amo")
    public ResponseEntity<List<TypePrimeIndemniteRetenue>> bySoumisAMO(@RequestParam Boolean flag) {
        return ResponseEntity.ok(service.findBySoumisAMO(flag));
    }

    // 🔍 Recherche soumisCIMR
    @GetMapping("/search/soumis-cimr")
    public ResponseEntity<List<TypePrimeIndemniteRetenue>> bySoumisCIMR(@RequestParam Boolean flag) {
        return ResponseEntity.ok(service.findBySoumisCIMR(flag));
    }

    // 🔍 Recherche soumisIR
    @GetMapping("/search/soumis-ir")
    public ResponseEntity<List<TypePrimeIndemniteRetenue>> bySoumisIR(@RequestParam Boolean flag) {
        return ResponseEntity.ok(service.findBySoumisIR(flag));
    }
}
