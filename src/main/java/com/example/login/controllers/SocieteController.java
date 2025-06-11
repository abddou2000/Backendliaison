package com.example.login.controllers;

import com.example.login.models.Societe;
import com.example.login.repositories.SocieteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/societes")
@CrossOrigin(origins = "*")
public class SocieteController {

    @Autowired
    private SocieteRepository societeRepository;

    // ➕ Ajouter une société
    @PostMapping
    public ResponseEntity<Societe> createSociete(@RequestBody Societe societe) {
        Societe saved = societeRepository.save(societe);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // 📄 Lister toutes les sociétés
    @GetMapping
    public ResponseEntity<List<Societe>> getAllSocietes() {
        List<Societe> list = societeRepository.findAll();
        return ResponseEntity.ok(list);
    }

    // 🔍 Rechercher une société par ID
    @GetMapping("/{idSociete}")
    public ResponseEntity<Societe> getSocieteById(@PathVariable String idSociete) {
        return societeRepository.findById(idSociete)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔍 Rechercher une société par nom
    @GetMapping("/search")
    public ResponseEntity<Societe> getSocieteByNom(@RequestParam String nomSociete) {
        return societeRepository.findByNomSociete(nomSociete)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✏️ Modifier une société
    @PutMapping("/{idSociete}")
    public ResponseEntity<Societe> updateSociete(
            @PathVariable String idSociete,
            @RequestBody Societe societeDetails) {

        return societeRepository.findById(idSociete)
                .map(existing -> {
                    existing.setNomSociete(societeDetails.getNomSociete());
                    existing.setAdresse(societeDetails.getAdresse());
                    existing.setVille(societeDetails.getVille());
                    existing.setIdentifiantFiscal(societeDetails.getIdentifiantFiscal());
                    existing.setNumeroCnss(societeDetails.getNumeroCnss());
                    existing.setNumeroIce(societeDetails.getNumeroIce());
                    existing.setNumeroRc(societeDetails.getNumeroRc());
                    existing.setDateDebut(societeDetails.getDateDebut());
                    existing.setDateFin(societeDetails.getDateFin());
                    existing.setNomBanque(societeDetails.getNomBanque());
                    existing.setRib(societeDetails.getRib());
                    existing.setBic(societeDetails.getBic());
                    return ResponseEntity.ok(societeRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 🗑 Supprimer une société
    @DeleteMapping("/{idSociete}")
    public ResponseEntity<Void> delete(@PathVariable String idSociete) {
        return societeRepository.findById(idSociete)
                .map(existing -> {
                    societeRepository.deleteById(idSociete);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
