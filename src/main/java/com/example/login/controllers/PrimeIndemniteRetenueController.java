package com.example.login.controllers;

import com.example.login.models.PrimeIndemniteRetenue;
import com.example.login.repositories.PrimeIndemniteRetenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/primes-indemnites")
@CrossOrigin(origins = "*")
public class PrimeIndemniteRetenueController {

    private final PrimeIndemniteRetenueRepository repository;

    @Autowired
    public PrimeIndemniteRetenueController(PrimeIndemniteRetenueRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<PrimeIndemniteRetenue> create(@RequestBody PrimeIndemniteRetenue prime) {
        return ResponseEntity.status(201).body(repository.save(prime));
    }

    @GetMapping
    public List<PrimeIndemniteRetenue> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrimeIndemniteRetenue> getOne(@PathVariable String id) {
        Optional<PrimeIndemniteRetenue> optional = repository.findById(id);
        return optional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrimeIndemniteRetenue> update(@PathVariable String id, @RequestBody PrimeIndemniteRetenue updated) {
        return repository.findById(id)
                .map(existing -> {
                    updated.setIdPrime(id); // ensure ID is preserved
                    return ResponseEntity.ok(repository.save(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return repository.findById(id)
                .map(prime -> {
                    repository.delete(prime);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-employe/{idEmploye}")
    public List<PrimeIndemniteRetenue> getByEmploye(@PathVariable String idEmploye) {
        return repository.findByEmploye_IdEmploye(idEmploye);
    }

    @GetMapping("/by-periode/{idPeriode}")
    public List<PrimeIndemniteRetenue> getByPeriode(@PathVariable String idPeriode) {
        return repository.findByPeriodePaie_IdPeriode(idPeriode);
    }

    @GetMapping("/by-typeprime/{idType}")
    public List<PrimeIndemniteRetenue> getByTypePrime(@PathVariable String idType) {
        return repository.findByTypePrime_IdTypePrime(idType);
    }
}
