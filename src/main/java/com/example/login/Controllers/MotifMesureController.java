package com.example.login.Controllers;

import com.example.login.Models.MotifMesure;
import com.example.login.Services.MotifMesureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motifs-mesures")
@CrossOrigin(origins = "*")
public class MotifMesureController {

    @Autowired
    private MotifMesureService service;

    // CREATE
    @PostMapping
    public ResponseEntity<MotifMesure> create(@RequestBody MotifMesure motif) {
        return ResponseEntity.ok(service.create(motif));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<MotifMesure>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<MotifMesure> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // READ BY MESURE
    @GetMapping("/mesure/{idMesure}")
    public ResponseEntity<List<MotifMesure>> getByMesure(@PathVariable String idMesure) {
        return ResponseEntity.ok(service.getByMesure(idMesure));
    }

    // READ BY TYPE
    @GetMapping("/type/{idTypeMotifMesure}")
    public ResponseEntity<List<MotifMesure>> getByTypeMotif(@PathVariable String idTypeMotifMesure) {
        return ResponseEntity.ok(service.getByTypeMotif(idTypeMotifMesure));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<MotifMesure> update(@PathVariable String id, @RequestBody MotifMesure updated) {
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
