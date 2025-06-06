package com.example.login.Controllers;

import com.example.login.Models.PeriodePaie;
import com.example.login.Services.PeriodePaieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/periodes-paie")
@CrossOrigin(origins = "*")
public class PeriodePaieController {

    @Autowired
    private PeriodePaieService service;

    // CREATE
    @PostMapping
    public ResponseEntity<PeriodePaie> create(@RequestBody PeriodePaie periode) {
        return ResponseEntity.ok(service.create(periode));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<PeriodePaie>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<PeriodePaie> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // READ BY YEAR
    @GetMapping("/annee/{annee}")
    public ResponseEntity<List<PeriodePaie>> getByAnnee(@PathVariable Integer annee) {
        return ResponseEntity.ok(service.getByAnnee(annee));
    }

    // READ BY STATE
    @GetMapping("/etat/{etat}")
    public ResponseEntity<List<PeriodePaie>> getByEtat(@PathVariable String etat) {
        return ResponseEntity.ok(service.getByEtat(etat));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<PeriodePaie> update(@PathVariable String id, @RequestBody PeriodePaie updated) {
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
