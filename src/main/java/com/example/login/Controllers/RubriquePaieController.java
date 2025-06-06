package com.example.login.Controllers;

import com.example.login.Models.RubriquePaie;
import com.example.login.Services.RubriquePaieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/rubriques-paie")
@CrossOrigin(origins = "*")
public class RubriquePaieController {

    private final RubriquePaieService service;

    @Autowired
    public RubriquePaieController(RubriquePaieService service) {
        this.service = service;
    }

    // ➕ Create
    @PostMapping
    public ResponseEntity<RubriquePaie> create(@RequestBody RubriquePaie r) {
        return ResponseEntity.status(201).body(service.create(r));
    }

    // 📄 List all
    @GetMapping
    public ResponseEntity<List<RubriquePaie>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    // 🔍 Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<RubriquePaie> getById(@PathVariable String id) {
        RubriquePaie r = service.getById(id);
        if (r == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(r);
    }


    @PutMapping("/{id}")
    public ResponseEntity<RubriquePaie> update(
            @PathVariable String id,
            @RequestBody RubriquePaie details) {
        RubriquePaie updated = service.update(id, details);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!service.delete(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    // 🔍 Search by code
    @GetMapping("/search/code")
    public ResponseEntity<List<RubriquePaie>> byCode(@RequestParam String code) {
        return ResponseEntity.ok(service.findByCode(code));
    }

    // 🔍 Search by type
    @GetMapping("/search/type")
    public ResponseEntity<List<RubriquePaie>> byType(@RequestParam String type) {
        return ResponseEntity.ok(service.findByType(type));
    }

    // 🔍 Search active at date (start ≤ date ≤ end)
    @GetMapping("/search/period")
    public ResponseEntity<List<RubriquePaie>> byPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) {
        return ResponseEntity.ok(service.findByPeriod(start, end));
    }

    // 🔍 Search ending after date
    @GetMapping("/search/ending-after")
    public ResponseEntity<List<RubriquePaie>> endingAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return ResponseEntity.ok(service.findEndingAfter(date));
    }

    // 🔍 Search by libellé fragment
    @GetMapping("/search/libelle")
    public ResponseEntity<List<RubriquePaie>> byLibelle(
            @RequestParam String fragment) {
        return ResponseEntity.ok(service.findByLibelleLike(fragment));
    }
}
