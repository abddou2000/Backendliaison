// src/main/java/com/example/login/Controllers/TypeMotifMesureController.java
package com.example.login.Controllers;

import com.example.login.Models.TypeMotifMesure;
import com.example.login.Services.TypeMotifMesureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @PostMapping
    public ResponseEntity<TypeMotifMesure> create(@RequestBody TypeMotifMesure t) {
        return ResponseEntity.status(201).body(service.create(t));
    }

    @GetMapping
    public ResponseEntity<List<TypeMotifMesure>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeMotifMesure> getById(@PathVariable String id) {
        TypeMotifMesure t = service.getById(id);
        return t == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(t);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeMotifMesure> update(@PathVariable String id, @RequestBody TypeMotifMesure details) {
        TypeMotifMesure updated = service.update(id, details);
        return updated == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/search/code")
    public ResponseEntity<List<TypeMotifMesure>> byCode(@RequestParam String code) {
        return ResponseEntity.ok(service.findByCode(code));
    }

    @GetMapping("/search/libelle")
    public ResponseEntity<List<TypeMotifMesure>> byLibelle(@RequestParam String libelle) {
        return ResponseEntity.ok(service.findByLibelle(libelle));
    }

    @GetMapping("/search/period")
    public ResponseEntity<List<TypeMotifMesure>> byPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) {
        return ResponseEntity.ok(service.findByPeriod(start, end));
    }

    @GetMapping("/search/started-after")
    public ResponseEntity<List<TypeMotifMesure>> startedAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return ResponseEntity.ok(service.findStartedAfter(date));
    }

    @GetMapping("/search/ending-before")
    public ResponseEntity<List<TypeMotifMesure>> endingBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return ResponseEntity.ok(service.findEndingBefore(date));
    }
}
