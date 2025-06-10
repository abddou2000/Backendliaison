package com.example.login.controllers;

import com.example.login.models.TypeContrat;
import com.example.login.Services.TypeContratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/types-contrats")
@CrossOrigin(origins = "*")
public class TypeContratController {

    private final TypeContratService service;

    @Autowired
    public TypeContratController(TypeContratService service) {
        this.service = service;
    }

    /** Création d’un nouveau type de contrat */
    @PostMapping
    public ResponseEntity<TypeContrat> create(@RequestBody TypeContrat contrat) {
        TypeContrat saved = service.create(contrat);
        return ResponseEntity.status(201).body(saved);
    }

    /** Liste tous les types de contrats */
    @GetMapping
    public ResponseEntity<List<TypeContrat>> getAll() {
        List<TypeContrat> list = service.listAll();
        return ResponseEntity.ok(list);
    }

    /** Récupère un type de contrat par code */
    @GetMapping("/{code}")
    public ResponseEntity<TypeContrat> getByCode(@PathVariable String code) {
        return service.findByCode(code)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /** Recherche un type de contrat par son nom */
    @GetMapping("/search/nom")
    public ResponseEntity<TypeContrat> getByNom(@RequestParam String nomContrat) {
        return service.findByNom(nomContrat)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /** Met à jour un type de contrat existant */
    @PutMapping("/{code}")
    public ResponseEntity<TypeContrat> update(
            @PathVariable String code,
            @RequestBody TypeContrat details) {

        return service.update(code, details)
                .map(updated -> ResponseEntity.ok(updated))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /** Supprime un type de contrat */
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        boolean deleted = service.delete(code);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}