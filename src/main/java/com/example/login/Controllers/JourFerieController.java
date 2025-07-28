
// src/main/java/com/example/login/Controllers/JourFerieController.java
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

    private final JourFerieService service;

    @Autowired
    public JourFerieController(JourFerieService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<JourFerie> create(@RequestBody JourFerie jf) {
        return ResponseEntity.status(201).body(service.create(jf));
    }

    @GetMapping
    public ResponseEntity<List<JourFerie>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JourFerie> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/recurrents")
    public ResponseEntity<List<JourFerie>> getRecurrent() {
        return ResponseEntity.ok(service.getRecurrentHolidays());
    }

    @PutMapping("/{id}")
    public ResponseEntity<JourFerie> update(@PathVariable String id, @RequestBody JourFerie updated) {
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