package com.example.login.Controllers;

import com.example.login.Models.Mesure;
import com.example.login.Services.MesureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesures")
@CrossOrigin(origins = "*")
public class MesureController {

    @Autowired
    private MesureService service;

    // CREATE
    @PostMapping
    public ResponseEntity<Mesure> create(@RequestBody Mesure mesure) {
        return ResponseEntity.ok(service.create(mesure));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Mesure>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Mesure> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CORRECTED METHOD: Changed @PathVariable to Long
    // READ BY EMPLOYE
    @GetMapping("/employe/{idEmploye}")
    public ResponseEntity<List<Mesure>> getByEmploye(@PathVariable Long idEmploye) {
        return ResponseEntity.ok(service.getByEmploye(idEmploye));
    }

    // READ BY TYPE
    @GetMapping("/type/{idTypeMesure}")
    public ResponseEntity<List<Mesure>> getByType(@PathVariable String idTypeMesure) {
        return ResponseEntity.ok(service.getByType(idTypeMesure));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Mesure> update(@PathVariable String id, @RequestBody Mesure updated) {
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