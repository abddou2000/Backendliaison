
// src/main/java/com/example/login/Controllers/TypeMesureController.java
package com.example.login.Controllers;

import com.example.login.Models.TypeMesure;
import com.example.login.Services.TypeMesureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @PostMapping
    public ResponseEntity<TypeMesure> create(@RequestBody TypeMesure t) {
        return ResponseEntity.status(201).body(service.create(t));
    }

    @GetMapping
    public ResponseEntity<List<TypeMesure>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeMesure> getById(@PathVariable String id) {
        TypeMesure t = service.getById(id);
        return t == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(t);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeMesure> update(@PathVariable String id, @RequestBody TypeMesure details) {
        TypeMesure updated = service.update(id, details);
        return updated == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/search/code")
    public ResponseEntity<List<TypeMesure>> byCode(@RequestParam String code) {
        return ResponseEntity.ok(service.findByCode(code));
    }

    @GetMapping("/search/nom")
    public ResponseEntity<List<TypeMesure>> byNom(@RequestParam String nom) {
        return ResponseEntity.ok(service.findByNom(nom));
    }

    @GetMapping("/search/embauche")
    public ResponseEntity<List<TypeMesure>> byEmbauche(@RequestParam Boolean embauche) {
        return ResponseEntity.ok(service.findByEmbauche(embauche));
    }

    @GetMapping("/search/period")
    public ResponseEntity<List<TypeMesure>> byPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) {
        return ResponseEntity.ok(service.findByPeriod(start, end));
    }

    @GetMapping("/search/started-after")
    public ResponseEntity<List<TypeMesure>> startedAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return ResponseEntity.ok(service.findStartedAfter(date));
    }

    @GetMapping("/search/ending-before")
    public ResponseEntity<List<TypeMesure>> endingBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return ResponseEntity.ok(service.findEndingBefore(date));
    }
}
