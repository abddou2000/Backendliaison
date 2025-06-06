package com.example.login.Controllers;

import com.example.login.Models.DeclarationSociale;
import com.example.login.Services.DeclarationSocialeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/declarations-sociales")
@CrossOrigin(origins = "*")
public class DeclarationSocialeController {

    @Autowired
    private DeclarationSocialeService service;

    // CREATE
    @PostMapping
    public ResponseEntity<DeclarationSociale> create(@RequestBody DeclarationSociale declaration) {
        return ResponseEntity.ok(service.create(declaration));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<DeclarationSociale>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<DeclarationSociale> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // READ BY PERIODE
    @GetMapping("/periode/{idPeriode}")
    public ResponseEntity<List<DeclarationSociale>> getByPeriode(@PathVariable String idPeriode) {
        return ResponseEntity.ok(service.getByPeriode(idPeriode));
    }

    // READ BY TYPE
    @GetMapping("/type/{type}")
    public ResponseEntity<List<DeclarationSociale>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(service.getByType(type));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<DeclarationSociale> update(@PathVariable String id, @RequestBody DeclarationSociale updated) {
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
