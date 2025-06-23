// src/main/java/com/example/login/Controllers/BaremeIRController.java
package com.example.login.Controllers;

import com.example.login.Models.BaremeIR;
import com.example.login.Repositories.BaremeIRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/baremes-ir")
@CrossOrigin(origins = "*")
public class BaremeIRController {

    @Autowired
    private BaremeIRRepository baremeIRRepository;

    // CREATE
    @PostMapping
    public ResponseEntity<BaremeIR> createBaremeIR(@RequestBody BaremeIR baremeIR) {
        BaremeIR saved = baremeIRRepository.save(baremeIR);
        return ResponseEntity.status(201).body(saved);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<BaremeIR>> getAllBaremesIR() {
        return ResponseEntity.ok(baremeIRRepository.findAll());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<BaremeIR> getBaremeIRById(@PathVariable String id) {
        Optional<BaremeIR> optional = baremeIRRepository.findById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<BaremeIR> updateBaremeIR(@PathVariable String id,
                                                   @RequestBody BaremeIR updatedData) {
        Optional<BaremeIR> existing = baremeIRRepository.findById(id);
        if (existing.isPresent()) {
            updatedData.setIdTranche(id);
            return ResponseEntity.ok(baremeIRRepository.save(updatedData));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBaremeIR(@PathVariable String id) {
        if (baremeIRRepository.existsById(id)) {
            baremeIRRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // FIND ACTIVE AT DATE
    @GetMapping("/actifs")
    public ResponseEntity<List<BaremeIR>> getBaremesActifs(@RequestParam("date") Date date) {
        List<BaremeIR> actifs = baremeIRRepository.findByDateDebutBeforeAndDateFinAfter(date, date);
        return ResponseEntity.ok(actifs);
    }
}
