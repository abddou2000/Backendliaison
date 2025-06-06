package com.example.login.Controllers;

import com.example.login.Models.JourFerie;
import com.example.login.Services.JourFerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jours-feries")
@CrossOrigin(origins = "*")
public class JourFerieController {

    @Autowired
    private JourFerieService service;

    // CREATE
    @PostMapping
    public ResponseEntity<JourFerie> create(@RequestBody JourFerie jourFerie) {
        return ResponseEntity.ok(service.create(jourFerie));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<JourFerie>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<JourFerie> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // READ BY PARAMETREUR
    @GetMapping("/parametreur/{idParametreur}")
    public ResponseEntity<List<JourFerie>> getByParametreur(@PathVariable String idParametreur) {
        return ResponseEntity.ok(service.getByParametreur(idParametreur));
    }

    // READ RECURRING HOLIDAYS
    @GetMapping("/recurrents")
    public ResponseEntity<List<JourFerie>> getRecurrentHolidays() {
        return ResponseEntity.ok(service.getRecurrentHolidays());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<JourFerie> update(@PathVariable String id, @RequestBody JourFerie updated) {
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
