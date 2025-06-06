package com.example.login.Controllers;

import com.example.login.Models.GrilleSalariale;
import com.example.login.Services.GrilleSalarialeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grilles-salariales")
@CrossOrigin(origins = "*")
public class GrilleSalarialeController {

    @Autowired
    private GrilleSalarialeService service;

    // CREATE
    @PostMapping
    public ResponseEntity<GrilleSalariale> create(@RequestBody GrilleSalariale grille) {
        return ResponseEntity.ok(service.create(grille));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<GrilleSalariale>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<GrilleSalariale> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<GrilleSalariale> update(@PathVariable String id, @RequestBody GrilleSalariale updated) {
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
