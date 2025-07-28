// src/main/java/com/example/login/Controllers/SocieteController.java
package com.example.login.Controllers;

import com.example.login.Models.Societe;
import com.example.login.Services.SocieteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/societes")
@CrossOrigin(origins = "*")
public class SocieteController {
    private final SocieteService service;

    @Autowired
    public SocieteController(SocieteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Societe> create(@RequestBody Societe s) {
        return ResponseEntity.status(201).body(service.save(s));
    }

    @GetMapping
    public ResponseEntity<List<Societe>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Societe> getById(@PathVariable String id) {
        Societe s = service.getById(id);
        return s == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(s);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Societe> update(@PathVariable String id, @RequestBody Societe details) {
        Societe updated = service.update(id, details);
        return updated == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/search/code")
    public ResponseEntity<List<Societe>> byCode(@RequestParam String code) {
        return ResponseEntity.ok(service.findByCode(code));
    }

    @GetMapping("/search/ville")
    public ResponseEntity<List<Societe>> byVille(@RequestParam String ville) {
        return ResponseEntity.ok(service.findByVille(ville));
    }

    @GetMapping("/search/period")
    public ResponseEntity<List<Societe>> byPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) {
        return ResponseEntity.ok(service.findByPeriod(start, end));
    }

    @GetMapping("/search/started-after")
    public ResponseEntity<List<Societe>> startedAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return ResponseEntity.ok(service.findStartedAfter(date));
    }

    @GetMapping("/search/ending-before")
    public ResponseEntity<List<Societe>> endingBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return ResponseEntity.ok(service.findEndingBefore(date));
    }

    @GetMapping("/search/banque")
    public ResponseEntity<List<Societe>> byBanque(@RequestParam String nomBanque) {
        return ResponseEntity.ok(service.findByNomBanque(nomBanque));
    }
}
