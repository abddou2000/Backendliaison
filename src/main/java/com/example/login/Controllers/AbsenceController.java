package com.example.login.Controllers;

import com.example.login.Models.Absence;
import com.example.login.Services.AbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/absences")
@CrossOrigin(origins = "*")
public class AbsenceController {

    @Autowired
    private AbsenceService service;

    @PostMapping
    public ResponseEntity<Absence> create(@RequestBody Absence absence) {
        return ResponseEntity.ok(service.create(absence));
    }

    @GetMapping
    public ResponseEntity<List<Absence>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Absence> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // This method correctly uses the Long type for the employee ID
    @GetMapping("/employe/{idEmploye}")
    public ResponseEntity<List<Absence>> getByEmploye(@PathVariable Long idEmploye) {
        return ResponseEntity.ok(service.getByEmploye(idEmploye));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Absence> update(@PathVariable String id, @RequestBody Absence updated) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.update(id, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}