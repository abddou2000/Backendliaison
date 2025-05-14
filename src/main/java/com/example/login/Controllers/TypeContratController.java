package com.example.login.Controllers;

import com.example.login.Models.TypeContrat;
import com.example.login.Repositories.TypeContratRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/types-contrats")
@CrossOrigin(origins = "*")
public class TypeContratController {

    @Autowired
    private TypeContratRepository contratRepo;

    @PostMapping
    public ResponseEntity<TypeContrat> create(@RequestBody TypeContrat contrat) {
        TypeContrat saved = contratRepo.save(contrat);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<TypeContrat>> getAll() {
        return ResponseEntity.ok(contratRepo.findAll());
    }

    @GetMapping("/{code}")
    public ResponseEntity<TypeContrat> getByCode(@PathVariable String code) {
        return contratRepo.findById(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/nom")
    public ResponseEntity<TypeContrat> getByNom(@RequestParam String nomContrat) {
        return contratRepo.findByNomContrat(nomContrat)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{code}")
    public ResponseEntity<TypeContrat> update(@PathVariable String code,
                                              @RequestBody TypeContrat details) {
        return contratRepo.findById(code)
                .map(existing -> {
                    existing.setNomContrat(details.getNomContrat());
                    existing.setPeriodeEssai(details.getPeriodeEssai());
                    existing.setDateDebut(details.getDateDebut());
                    existing.setDateFin(details.getDateFin());
                    existing.setConditionsSpecifiques(details.getConditionsSpecifiques());
                    return ResponseEntity.ok(contratRepo.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        Optional<TypeContrat> opt = contratRepo.findById(code);
        if (opt.isPresent()) {
            contratRepo.deleteById(code);
            return ResponseEntity.noContent().build(); // ✅ ici le bon type
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
