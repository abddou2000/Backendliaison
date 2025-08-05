package com.example.login.Controllers;

import com.example.login.Models.PrimeIndemniteRetenue;
import com.example.login.Services.PrimeIndemniteRetenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/primes-indemnites")
@CrossOrigin(origins = "*")
public class PrimeIndemniteRetenueController {

    @Autowired
    private PrimeIndemniteRetenueService service;

    // CREATE
    @PostMapping
    public ResponseEntity<PrimeIndemniteRetenue> create(@RequestBody PrimeIndemniteRetenue prime) {
        return ResponseEntity.ok(service.create(prime));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<PrimeIndemniteRetenue>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<PrimeIndemniteRetenue> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CORRECTED METHOD: Changed @PathVariable to Long
    // READ BY EMPLOYE
    @GetMapping("/employe/{idEmploye}")
    public ResponseEntity<List<PrimeIndemniteRetenue>> getByEmploye(@PathVariable Long idEmploye) {
        return ResponseEntity.ok(service.getByEmploye(idEmploye));
    }

    // READ BY PERIODE
    @GetMapping("/periode/{idPeriode}")
    public ResponseEntity<List<PrimeIndemniteRetenue>> getByPeriode(@PathVariable String idPeriode) {
        return ResponseEntity.ok(service.getByPeriode(idPeriode));
    }

    // READ BY TYPE
    @GetMapping("/type/{idTypePrime}")
    public ResponseEntity<List<PrimeIndemniteRetenue>> getByType(@PathVariable String idTypePrime) {
        return ResponseEntity.ok(service.getByType(idTypePrime));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<PrimeIndemniteRetenue> update(@PathVariable String id, @RequestBody PrimeIndemniteRetenue updated) {
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