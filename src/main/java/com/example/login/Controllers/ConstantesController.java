package com.example.login.Controllers;

import com.example.login.Models.Constantes;
import com.example.login.Services.ConstantesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/constantes")
@CrossOrigin(origins = "*")
public class ConstantesController {

    @Autowired
    private ConstantesService service;

    // CREATE
    @PostMapping
    public ResponseEntity<Constantes> create(@RequestBody Constantes constante) {
        return ResponseEntity.ok(service.create(constante));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Constantes>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Constantes> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // READ BY CODE
    @GetMapping("/code/{code}")
    public ResponseEntity<List<Constantes>> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(service.getByCode(code));
    }

    // READ BY CONFIGURATEUR
    @GetMapping("/configurateur/{id}")
    public ResponseEntity<List<Constantes>> getByConfigurateur(@PathVariable String id) {
        return ResponseEntity.ok(service.getByConfigurateur(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Constantes> update(@PathVariable String id, @RequestBody Constantes updated) {
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
