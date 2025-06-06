package com.example.login.Controllers;

import com.example.login.Models.TypeAbsence;
import com.example.login.Services.TypeAbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/types-absences")
@CrossOrigin(origins = "*")
public class TypeAbsenceController {

    @Autowired
    private TypeAbsenceService service;

    // CREATE
    @PostMapping
    public ResponseEntity<TypeAbsence> create(@RequestBody TypeAbsence typeAbsence) {
        return ResponseEntity.ok(service.create(typeAbsence));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<TypeAbsence>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<TypeAbsence> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<TypeAbsence> update(@PathVariable String id, @RequestBody TypeAbsence updated) {
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
