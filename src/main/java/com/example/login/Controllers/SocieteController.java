package com.example.login.Controllers;

import com.example.login.Models.Societe;
import com.example.login.Repositories.SocieteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/societes")
public class SocieteController {

    @Autowired
    private SocieteRepository societeRepository;

    // CREATE a new Societe
    @PostMapping
    public ResponseEntity<Societe> createSociete(@RequestBody Societe societe) {
        Societe saved = societeRepository.save(societe);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // READ all Societes
    @GetMapping
    public ResponseEntity<List<Societe>> getAllSocietes() {
        List<Societe> list = societeRepository.findAll();
        return ResponseEntity.ok(list);
    }

    // READ a Societe by idSociete
    @GetMapping("/{idSociete}")
    public ResponseEntity<Societe> getSocieteById(@PathVariable String idSociete) {
        Optional<Societe> optional = societeRepository.findByIdSociete(idSociete);
        return optional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
        ///afficher tous les societes//
    }

    // READ a Societe by raisonSociale
    @GetMapping("/search")
    public ResponseEntity<Societe> getSocieteByRaison(@RequestParam String raisonSociale) {
        Optional<Societe> optional = societeRepository.findByRaisonSociale(raisonSociale);
        return optional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UPDATE an existing Societe
    @PutMapping("/{idSociete}")
    public ResponseEntity<Societe> updateSociete(
            @PathVariable String idSociete,
            @RequestBody Societe societeDetails) {
        return societeRepository.findById(idSociete)
                .map(existing -> {
                    existing.setRaisonSociale(societeDetails.getRaisonSociale());
                    existing.setAdresse(societeDetails.getAdresse());
                    existing.setTelephone(societeDetails.getTelephone());
                    existing.setEmail(societeDetails.getEmail());
                    existing.setSiteWeb(societeDetails.getSiteWeb());
                    existing.setRc(societeDetails.getRc());
                    existing.setIce(societeDetails.getIce());
                    existing.setIfFiscal(societeDetails.getIfFiscal());
                    existing.setCnss(societeDetails.getCnss());
                    existing.setDateCreation(societeDetails.getDateCreation());
                    Societe updated = societeRepository.save(existing);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE a Societe by idSociete
    @DeleteMapping("/{idSociete}")
    public ResponseEntity<Void> deleteSociete(@PathVariable String idSociete) {
        return societeRepository.findById(idSociete)
                .map(existing -> {
                    societeRepository.deleteById(idSociete);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
