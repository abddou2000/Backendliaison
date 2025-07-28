// src/main/java/com/example/login/Controllers/ConstantesController.java
package com.example.login.Controllers;

import com.example.login.Models.Constantes;
import com.example.login.Services.ConstantesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/constantes")
@CrossOrigin(origins = "*")
public class ConstantesController {

    private final ConstantesService service;

    @Autowired
    public ConstantesController(ConstantesService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Constantes> create(@RequestBody Constantes c) {
        return ResponseEntity.status(201).body(service.create(c));
    }

    @GetMapping
    public ResponseEntity<List<Constantes>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Constantes> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/code")
    public ResponseEntity<List<Constantes>> byCode(@RequestParam String code) {
        return ResponseEntity.ok(service.getByCode(code));
    }

    @GetMapping("/search/nom")
    public ResponseEntity<List<Constantes>> byNom(@RequestParam String nom) {
        return ResponseEntity.ok(service.getByNom(nom));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Constantes> update(@PathVariable String id, @RequestBody Constantes updated) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.update(id, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/period")
    public ResponseEntity<List<Constantes>> byPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) {
        return ResponseEntity.ok(service.findByPeriod(start, end));
    }

    @GetMapping("/search/started-after")
    public ResponseEntity<List<Constantes>> startedAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return ResponseEntity.ok(service.findStartedAfter(date));
    }

    @GetMapping("/search/ending-before")
    public ResponseEntity<List<Constantes>> endingBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return ResponseEntity.ok(service.findEndingBefore(date));
    }
}
