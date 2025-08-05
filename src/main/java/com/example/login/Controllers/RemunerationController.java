package com.example.login.Controllers;

import com.example.login.Models.Remuneration;
import com.example.login.Services.RemunerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/remunerations")
@CrossOrigin(origins = "*")
public class RemunerationController {

    @Autowired
    private RemunerationService service;

    @PostMapping
    public ResponseEntity<Remuneration> create(@RequestBody Remuneration remuneration) {
        return ResponseEntity.status(201).body(service.create(remuneration));
    }

    @GetMapping
    public ResponseEntity<List<Remuneration>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Remuneration> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CORRECTED METHOD: Changed @PathVariable to Long
    @GetMapping("/employe/{idEmploye}")
    public ResponseEntity<Remuneration> getByEmploye(@PathVariable Long idEmploye) {
        return service.getByEmploye(idEmploye)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Remuneration> update(@PathVariable String id, @RequestBody Remuneration updated) {
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
}