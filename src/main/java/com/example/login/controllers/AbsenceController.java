package com.example.login.controllers;

import com.example.login.models.Absence;
import com.example.login.repositories.AbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/absences")
@CrossOrigin(origins = "*")
public class AbsenceController {

    private final AbsenceRepository absenceRepository;

    @Autowired
    public AbsenceController(AbsenceRepository absenceRepository) {
        this.absenceRepository = absenceRepository;
    }

    // CREATE absence
    @PostMapping
    public ResponseEntity<Absence> createAbsence(@RequestBody Absence absence) {
        if (absence.getIdAbsence() == null || absence.getIdAbsence().isBlank()) {
            absence.setIdAbsence(UUID.randomUUID().toString());
        }
        Absence saved = absenceRepository.save(absence);
        return ResponseEntity.status(201).body(saved);
    }

    // READ all absences
    @GetMapping
    public ResponseEntity<List<Absence>> listAll() {
        return ResponseEntity.ok(absenceRepository.findAll());
    }

    // READ by ID
    @GetMapping("/{id}")
    public ResponseEntity<Absence> getById(@PathVariable String id) {
        Optional<Absence> opt = absenceRepository.findById(id);
        return opt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UPDATE absence
    @PutMapping("/{id}")
    public ResponseEntity<Absence> updateAbsence(@PathVariable String id,
                                                 @RequestBody Absence details) {
        return absenceRepository.findById(id)
                .map(existing -> {
                    existing.setEmploye(details.getEmploye());
                    existing.setTypeAbsence(details.getTypeAbsence());
                    existing.setPeriodePaie(details.getPeriodePaie());
                    existing.setDateDebut(details.getDateDebut());
                    existing.setDateFin(details.getDateFin());
                    existing.setJustificatifRequis(details.isJustificatifRequis());
                    existing.setAbsenceRemuneree(details.isAbsenceRemuneree());
                    existing.setCode(details.getCode());
                    existing.setNom(details.getNom());
                    existing.setImpactSolde(details.isImpactSolde());
                    Absence updated = absenceRepository.save(existing);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE absence
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbsence(@PathVariable String id) {
        if (absenceRepository.existsById(id)) {
            absenceRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET by employe
    @GetMapping("/employe/{empId}")
    public ResponseEntity<List<Absence>> byEmploye(@PathVariable String empId) {
        return ResponseEntity.ok(absenceRepository.findByEmploye_IdEmploye(empId));
    }

    // GET by type absence
    @GetMapping("/type/{typeId}")
    public ResponseEntity<List<Absence>> byType(@PathVariable String typeId) {
        return ResponseEntity.ok(absenceRepository.findByTypeAbsence_IdTypeAbsence(typeId));
    }

    // GET by periode paie
    @GetMapping("/periode/{perId}")
    public ResponseEntity<List<Absence>> byPeriode(@PathVariable String perId) {
        return ResponseEntity.ok(absenceRepository.findByPeriodePaie_IdPeriode(perId));
    }
}
