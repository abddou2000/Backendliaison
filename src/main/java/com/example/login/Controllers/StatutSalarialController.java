package com.example.login.Controllers;

import com.example.login.Models.StatutSalarial;
import com.example.login.Repositories.StatutSalarialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/statuts-salariaux")
@CrossOrigin(origins = "*")
public class StatutSalarialController {

    @Autowired
    private StatutSalarialRepository statutRepo;

    // 🔹 1. Créer un statut
    @PostMapping
    public ResponseEntity<StatutSalarial> createStatut(@RequestBody StatutSalarial statut) {
        StatutSalarial saved = statutRepo.save(statut);
        return ResponseEntity.status(201).body(saved);
    }

    // 🔹 2. Lister tous les statuts
    @GetMapping
    public ResponseEntity<List<StatutSalarial>> getAllStatuts() {
        return ResponseEntity.ok(statutRepo.findAll());
    }

    // 🔹 3. Rechercher par ID
    @GetMapping("/{id}")
    public ResponseEntity<StatutSalarial> getById(@PathVariable String id) {
        return statutRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 4. Rechercher par code
    @GetMapping("/search/code")
    public ResponseEntity<StatutSalarial> getByCode(@RequestParam String codeStatut) {
        return statutRepo.findByCodeStatut(codeStatut)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 5. Rechercher par nom
    @GetMapping("/search/nom")
    public ResponseEntity<StatutSalarial> getByNom(@RequestParam String nomStatut) {
        return statutRepo.findByNomStatut(nomStatut)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 6. Modifier un statut
    @PutMapping("/{id}")
    public ResponseEntity<StatutSalarial> updateStatut(@PathVariable String id,
                                                       @RequestBody StatutSalarial details) {
        return statutRepo.findById(id)
                .map(existing -> {
                    existing.setCodeStatut(details.getCodeStatut());
                    existing.setNomStatut(details.getNomStatut());
                    existing.setDescriptionStatut(details.getDescriptionStatut());
                    existing.setRaisonInactivite(details.getRaisonInactivite());
                    existing.setDateDebut(details.getDateDebut());
                    existing.setDateFin(details.getDateFin());
                    return ResponseEntity.ok(statutRepo.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 7. Supprimer un statut
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatut(@PathVariable String id) {
        Optional<StatutSalarial> optional = statutRepo.findById(id);
        if (optional.isPresent()) {
            statutRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

} // ✅ FIN de la classe (manquait cette accolade)
