package com.example.login.Controllers;

import com.example.login.Models.ParametresSysteme;
import com.example.login.Services.ParametresSystemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parametres-systeme")
@CrossOrigin(origins = "*")
public class ParametresSystemeController {

    @Autowired
    private ParametresSystemeService service;

    // CREATE
    @PostMapping
    public ResponseEntity<ParametresSysteme> create(@RequestBody ParametresSysteme param) {
        return ResponseEntity.ok(service.create(param));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<ParametresSysteme>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ParametresSysteme> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ParametresSysteme> update(@PathVariable String id, @RequestBody ParametresSysteme updated) {
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
