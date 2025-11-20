package com.example.login.Controllers;

import com.example.login.Models.DemandeConge;
import com.example.login.Services.DemandeCongeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/demandes-conge")
@CrossOrigin(origins = "*")
public class DemandeCongeController {

    private final DemandeCongeService demandeCongeService;

    public DemandeCongeController(DemandeCongeService demandeCongeService) {
        this.demandeCongeService = demandeCongeService;
    }

    // DTO pour création
    public static class DemandeCongeRequest {
        public Long idEmploye;
        public DemandeConge.TypeConge typeConge;
        public LocalDate dateDebut;
        public LocalDate dateFin;
        public String motif;
        public String pieceJointe;
    }

    public static class ChangerStatutRequest {
        public DemandeConge.StatutDemandeConge statut;
        public String commentaireManager;
    }

    // 🔹 Liste toutes les demandes
    @GetMapping
    public List<DemandeConge> getAll() {
        return demandeCongeService.getAll();
    }

    // 🔹 Créer une demande
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DemandeConge creerDemande(@RequestBody DemandeCongeRequest request) {
        return demandeCongeService.creerDemande(
                request.idEmploye,
                request.typeConge,
                request.dateDebut,
                request.dateFin,
                request.motif,
                request.pieceJointe
        );
    }

    // 🔹 Demande par ID
    @GetMapping("/{id}")
    public DemandeConge getById(@PathVariable Long id) {
        return demandeCongeService.getById(id);
    }

    // 🔹 Demandes d’un employé
    @GetMapping("/employe/{idEmploye}")
    public List<DemandeConge> getByEmploye(@PathVariable Long idEmploye) {
        return demandeCongeService.getByEmploye(idEmploye);
    }

    // 🔹 Par type de congé
    @GetMapping("/type/{typeConge}")
    public List<DemandeConge> getByType(@PathVariable DemandeConge.TypeConge typeConge) {
        return demandeCongeService.getByType(typeConge);
    }

    // 🔹 Par statut
    @GetMapping("/statut/{statut}")
    public List<DemandeConge> getByStatut(@PathVariable DemandeConge.StatutDemandeConge statut) {
        return demandeCongeService.getByStatut(statut);
    }

    // 🔹 Changer le statut
    @PutMapping("/{id}/statut")
    public ResponseEntity<DemandeConge> changerStatut(
            @PathVariable Long id,
            @RequestBody ChangerStatutRequest request) {

        DemandeConge updated = demandeCongeService.changerStatut(
                id,
                request.statut,
                request.commentaireManager
        );
        return ResponseEntity.ok(updated);
    }

    // 🔹 Supprimer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        demandeCongeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
