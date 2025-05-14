package com.example.login.Controllers;

import com.example.login.Models.CategorieSalariale;
import com.example.login.Repositories.CategorieSalarialeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories-salariales")
public class CategorieSalarialeController {

    @Autowired
    private CategorieSalarialeRepository categorieRepo;

    // CREATE
    @PostMapping
    public ResponseEntity<CategorieSalariale> createCategorie(@RequestBody CategorieSalariale categorie) {
        CategorieSalariale saved = categorieRepo.save(categorie);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // READ all
    @GetMapping
    public ResponseEntity<List<CategorieSalariale>> getAllCategories() {
        return ResponseEntity.ok(categorieRepo.findAll());
    }

    // ✅ 🔁 Mettre les routes spécifiques AVANT la route par ID
    // READ by code
    @GetMapping("/search/code")
    public ResponseEntity<CategorieSalariale> getByCode(@RequestParam String codeCategorie) {
        return categorieRepo.findByCodeCategorie(codeCategorie)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // READ by name
    @GetMapping("/search/nom")
    public ResponseEntity<CategorieSalariale> getByNom(@RequestParam String nomCategorie) {
        return categorieRepo.findByNomCategorie(nomCategorie)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ ID est de type String !
    @GetMapping("/{id}")
    public ResponseEntity<CategorieSalariale> getCategorieById(@PathVariable String id) {
        return categorieRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CategorieSalariale> updateCategorie(
            @PathVariable String id,
            @RequestBody CategorieSalariale details) {
        return categorieRepo.findById(id)
                .map(existing -> {
                    existing.setCodeCategorie(details.getCodeCategorie());
                    existing.setNomCategorie(details.getNomCategorie());
                    existing.setDescriptionCategorie(details.getDescriptionCategorie());
                    existing.setDateDebut(details.getDateDebut());
                    existing.setDateFin(details.getDateFin());
                    existing.setStatutSalarial(details.getStatutSalarial());
                    return ResponseEntity.ok(categorieRepo.save(existing));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable String id) {
        return categorieRepo.findById(id)
                .map(existing -> {
                    categorieRepo.deleteById(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
