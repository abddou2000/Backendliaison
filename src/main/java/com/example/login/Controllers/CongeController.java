package com.example.login.Controllers;

import com.example.login.Models.Conge;
import com.example.login.Services.CongeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conges")
@CrossOrigin(origins = "*")
public class CongeController {

    @Autowired
    private CongeService service;

    // CREATE
    @PostMapping
    public ResponseEntity<Conge> create(@RequestBody Conge conge) {
        return ResponseEntity.ok(service.create(conge));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Conge>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Conge> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- THIS IS THE CORRECTED METHOD ---
    // READ BY EMPLOYE
    @GetMapping("/employe/{id_user}")
    public ResponseEntity<List<Conge>> getByEmploye(@PathVariable Long id_user) {
        return ResponseEntity.ok(service.getByEmploye(id_user));
    }
    // ------------------------------------

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Conge> update(@PathVariable String id, @RequestBody Conge updated) {
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