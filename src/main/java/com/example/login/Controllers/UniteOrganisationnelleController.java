package com.example.login.Controllers;

import com.example.login.Models.UniteOrganisationnelle;
import com.example.login.Repositories.UniteOrganisationnelleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/unites")
@CrossOrigin(origins = "*")
public class UniteOrganisationnelleController {

    @Autowired
    private UniteOrganisationnelleRepository uniteRepo;

    // ➕ Ajouter une unité
    @PostMapping
    public ResponseEntity<UniteOrganisationnelle> createUnite(@RequestBody UniteOrganisationnelle unite) {
        UniteOrganisationnelle saved = uniteRepo.save(unite);
        return ResponseEntity.status(201).body(saved);
    }

    // 📄 Lister toutes les unités
    @GetMapping
    public ResponseEntity<List<UniteOrganisationnelle>> getAll() {
        return ResponseEntity.ok(uniteRepo.findAll());
    }

    // 🔍 Recherche par ID
    @GetMapping("/{id}")
    public ResponseEntity<UniteOrganisationnelle> getById(@PathVariable String id) {
        return uniteRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔍 Recherche par code
    @GetMapping("/search/code")
    public ResponseEntity<UniteOrganisationnelle> getByCode(@RequestParam String codeUnite) {
        return uniteRepo.findByCodeUnite(codeUnite)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔍 Recherche par nom
    @GetMapping("/search/nom")
    public ResponseEntity<UniteOrganisationnelle> getByNom(@RequestParam String nomUnite) {
        return uniteRepo.findByNomUnite(nomUnite)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔍 Liste par type
    @GetMapping("/search/type")
    public ResponseEntity<List<UniteOrganisationnelle>> getByType(@RequestParam String typeUnite) {
        return ResponseEntity.ok(uniteRepo.findByTypeUnite(typeUnite));
    }

    // 🔍 Liste par société
    @GetMapping("/search/societe")
    public ResponseEntity<List<UniteOrganisationnelle>> getBySociete(@RequestParam String idSociete) {
        return ResponseEntity.ok(uniteRepo.findBySociete_IdSociete(idSociete));
    }

    // 🔍 Liste des sous-unités d’un parent
    @GetMapping("/search/parent")
    public ResponseEntity<List<UniteOrganisationnelle>> getByParent(@RequestParam String idUniteParent) {
        return ResponseEntity.ok(uniteRepo.findByUniteParent_IdUnite(idUniteParent));
    }

    // ✏️ Modifier une unité
    @PutMapping("/{id}")
    public ResponseEntity<UniteOrganisationnelle> updateUnite(@PathVariable String id,
                                                              @RequestBody UniteOrganisationnelle details) {
        return uniteRepo.findById(id)
                .map(existing -> {
                    existing.setCodeUnite(details.getCodeUnite());
                    existing.setNomUnite(details.getNomUnite());
                    existing.setTypeUnite(details.getTypeUnite());
                    existing.setUniteParent(details.getUniteParent());
                    existing.setSociete(details.getSociete());
                    existing.setDescriptionUnite(details.getDescriptionUnite());
                    existing.setDateDebut(details.getDateDebut());
                    existing.setDateFin(details.getDateFin());
                    return ResponseEntity.ok(uniteRepo.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ❌ Supprimer une unité
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnite(@PathVariable String id) {
        Optional<UniteOrganisationnelle> opt = uniteRepo.findById(id);
        if (opt.isPresent()) {
            uniteRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
