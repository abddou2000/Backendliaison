package com.example.login.Controllers;

import com.example.login.Models.ResultatPaie;
import com.example.login.Services.ResultatPaieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resultats-paie")
@CrossOrigin(origins = "*")
public class ResultatPaieController {

    @Autowired
    private ResultatPaieService service;

    @PostMapping
    public ResponseEntity<ResultatPaie> create(@RequestBody ResultatPaie dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ResultatPaie>> list() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultatPaie> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CORRECTED METHOD: Changed @PathVariable to Long
    @GetMapping("/employe/{empId}")
    public ResponseEntity<List<ResultatPaie>> byEmploye(@PathVariable Long empId) {
        return ResponseEntity.ok(service.getByEmploye(empId));
    }

    @GetMapping("/periode/{perId}")
    public ResponseEntity<List<ResultatPaie>> byPeriode(@PathVariable String perId) {
        return ResponseEntity.ok(service.getByPeriode(perId));
    }

    @GetMapping("/societe/{socId}")
    public ResponseEntity<List<ResultatPaie>> bySociete(@PathVariable String socId) {
        return ResponseEntity.ok(service.getBySociete(socId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultatPaie> update(@PathVariable String id, @RequestBody ResultatPaie dto) {
        if (!service.existsById(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!service.existsById(id)) return ResponseEntity.notFound().build();
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}