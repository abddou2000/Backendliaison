package com.example.login.Controllers;

import com.example.login.Models.ElementVariablePaie;
import com.example.login.Services.ElementVariablePaieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/elements-variables")
@CrossOrigin(origins = "*")
public class ElementVariablePaieController {

    @Autowired
    private ElementVariablePaieService service;

    // CREATE
    @PostMapping
    public ResponseEntity<ElementVariablePaie> create(@RequestBody ElementVariablePaie element) {
        return ResponseEntity.ok(service.create(element));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<ElementVariablePaie>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ElementVariablePaie> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CORRECTED METHOD: Changed @PathVariable to Long
    // READ BY EMPLOYE
    @GetMapping("/employe/{idEmploye}")
    public ResponseEntity<List<ElementVariablePaie>> getByEmploye(@PathVariable Long idEmploye) {
        return ResponseEntity.ok(service.getByEmploye(idEmploye));
    }

    // READ BY PERIODE
    @GetMapping("/periode/{idPeriode}")
    public ResponseEntity<List<ElementVariablePaie>> getByPeriode(@PathVariable String idPeriode) {
        return ResponseEntity.ok(service.getByPeriode(idPeriode));
    }

    // READ BY RUBRIQUE
    @GetMapping("/rubrique/{idRubrique}")
    public ResponseEntity<List<ElementVariablePaie>> getByRubrique(@PathVariable String idRubrique) {
        return ResponseEntity.ok(service.getByRubrique(idRubrique));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ElementVariablePaie> update(@PathVariable Long id, @RequestBody ElementVariablePaie updated) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.update(id, updated));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}