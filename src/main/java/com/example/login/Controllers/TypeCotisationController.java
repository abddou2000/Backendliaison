package com.example.login.Controllers;

import com.example.login.Models.TypeCotisation;
import com.example.login.Services.TypeCotisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/types-cotisation")
@CrossOrigin(origins = "*")
public class TypeCotisationController {

    private final TypeCotisationService service;

    @Autowired
    public TypeCotisationController(TypeCotisationService service) {
        this.service = service;
    }

    /** Créer une nouvelle TypeCotisation */
    @PostMapping
    public ResponseEntity<TypeCotisation> create(@RequestBody TypeCotisation t) {
        TypeCotisation saved = service.save(t);
        return ResponseEntity.status(201).body(saved);
    }

    /** Lister toutes les TypeCotisation */
    @GetMapping
    public ResponseEntity<List<TypeCotisation>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    /** Obtenir par ID */
    @GetMapping("/{id}")
    public ResponseEntity<TypeCotisation> getById(@PathVariable String id) {
        TypeCotisation t = service.getById(id);
        return t == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(t);
    }

    /** Mettre à jour (remplace tous les champs) */
    @PutMapping("/{id}")
    public ResponseEntity<TypeCotisation> update(
            @PathVariable String id,
            @RequestBody TypeCotisation details) {
        TypeCotisation updated = service.update(id, details);
        return updated == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(updated);
    }

    /** Supprimer */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean ok = service.delete(id);
        return ok
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    /** Recherche par nom exact */
    @GetMapping("/search/nom")
    public ResponseEntity<List<TypeCotisation>> byName(@RequestParam String nom) {
        return ResponseEntity.ok(service.findByName(nom));
    }

    /** Recherche par code exact */
    @GetMapping("/search/code")
    public ResponseEntity<List<TypeCotisation>> byCode(@RequestParam String code) {
        return ResponseEntity.ok(service.findByCode(code));
    }

    /** Recherche par période (dateDebut ≤ start ≤ dateFin) */
    @GetMapping("/search/period")
    public ResponseEntity<List<TypeCotisation>> byPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) {
        return ResponseEntity.ok(service.findByPeriod(start, end));
    }

    /** Recherche des cotisations démarrées après une date */
    @GetMapping("/search/started-after")
    public ResponseEntity<List<TypeCotisation>> startedAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return ResponseEntity.ok(service.findStartedAfter(date));
    }

    /** Recherche des cotisations terminées avant une date */
    @GetMapping("/search/ending-before")
    public ResponseEntity<List<TypeCotisation>> endingBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return ResponseEntity.ok(service.findEndingBefore(date));
    }
}
