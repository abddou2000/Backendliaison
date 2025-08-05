package com.example.login.Controllers;

import com.example.login.Models.SoldeConges;
import com.example.login.Services.SoldeCongesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solde-conges")
@CrossOrigin(origins = "*")
public class SoldeCongesController {

    @Autowired
    private SoldeCongesService service;

    // CREATE
    @PostMapping
    public ResponseEntity<SoldeConges> create(@RequestBody SoldeConges solde) {
        return ResponseEntity.ok(service.create(solde));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<SoldeConges>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<SoldeConges> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // READ BY EMPLOYE ID
    // CORRECTED METHOD 1: Changed @PathVariable to Long
    @GetMapping("/employe/{idEmploye}")
    public ResponseEntity<List<SoldeConges>> getByEmploye(@PathVariable Long idEmploye) {
        return ResponseEntity.ok(service.getByEmploye(idEmploye));
    }

    // READ BY EMPLOYE & ANNEE
    // CORRECTED METHOD 2: Changed @PathVariable to Long
    @GetMapping("/employe/{idEmploye}/annee/{annee}")
    public ResponseEntity<SoldeConges> getByEmployeAndAnnee(@PathVariable Long idEmploye, @PathVariable Integer annee) {
        return ResponseEntity.ok(service.getByEmployeAndAnnee(idEmploye, annee));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<SoldeConges> update(@PathVariable String id, @RequestBody SoldeConges updated) {
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