package com.example.login.Controllers;

import com.example.login.Models.Attestation;
import com.example.login.Repositories.AttestationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attestations")
@CrossOrigin(origins = "*")
public class AttestationController {

    @Autowired
    private AttestationRepository attestationRepo;

    @PostMapping
    public ResponseEntity<Attestation> create(@RequestBody Attestation attestation) {
        Attestation saved = attestationRepo.save(attestation);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Attestation>> getAll() {
        return ResponseEntity.ok(attestationRepo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attestation> getById(@PathVariable String id) {
        return attestationRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/nom")
    public ResponseEntity<Attestation> getByNom(@RequestParam String nomAttestation) {
        return attestationRepo.findByNomAttestation(nomAttestation)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attestation> update(@PathVariable String id, @RequestBody Attestation details) {
        return attestationRepo.findById(id)
                .map(existing -> {
                    existing.setNomAttestation(details.getNomAttestation());
                    existing.setTypeAttestation(details.getTypeAttestation());
                    return ResponseEntity.ok(attestationRepo.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        Optional<Attestation> optional = attestationRepo.findById(id);
        if (optional.isPresent()) {
            attestationRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
