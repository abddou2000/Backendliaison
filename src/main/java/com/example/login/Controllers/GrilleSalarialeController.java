package com.example.login.Controllers;

import com.example.login.Models.GrilleSalariale;
import com.example.login.Services.GrilleSalarialeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grilles-salariales")
@CrossOrigin(origins = "*")
public class GrilleSalarialeController {

    private final GrilleSalarialeService service;

    public GrilleSalarialeController(GrilleSalarialeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody GrilleSalariale grille) {
        try {
            return ResponseEntity.ok(service.create(grille));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<GrilleSalariale>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrilleSalariale> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<GrilleSalariale> getByCode(@PathVariable String code) {
        return service.getByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/echelon/{idEchelon}")
    public ResponseEntity<List<GrilleSalariale>> getByEchelon(@PathVariable String idEchelon) {
        return ResponseEntity.ok(service.getByEchelon(idEchelon));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody GrilleSalariale updated) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        try {
            return ResponseEntity.ok(service.update(id, updated));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
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
