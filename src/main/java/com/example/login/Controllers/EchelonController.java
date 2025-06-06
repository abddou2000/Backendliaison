package com.example.login.Controllers;

import com.example.login.Models.Echelon;
import com.example.login.Services.EchelonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/echelons")
@CrossOrigin(origins = "*")
public class EchelonController {

    @Autowired
    private EchelonService service;

    // CREATE
    @PostMapping
    public ResponseEntity<Echelon> create(@RequestBody Echelon echelon) {
        return ResponseEntity.ok(service.create(echelon));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Echelon>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Echelon> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // READ BY CATEGORIE
    @GetMapping("/categorie/{idCategorie}")
    public ResponseEntity<List<Echelon>> getByCategorie(@PathVariable String idCategorie) {
        return ResponseEntity.ok(service.getByCategorie(idCategorie));
    }

    // READ BY STATUT
    @GetMapping("/statut/{idStatut}")
    public ResponseEntity<List<Echelon>> getByStatut(@PathVariable String idStatut) {
        return ResponseEntity.ok(service.getByStatut(idStatut));
    }

    // READ BY GRILLE
    @GetMapping("/grille/{idGrille}")
    public ResponseEntity<List<Echelon>> getByGrille(@PathVariable String idGrille) {
        return ResponseEntity.ok(service.getByGrille(idGrille));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Echelon> update(@PathVariable String id, @RequestBody Echelon updated) {
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
