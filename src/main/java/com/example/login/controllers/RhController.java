package com.example.login.controllers;

import com.example.login.models.Rh;
import com.example.login.Services.RhService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rhs")
public class RhController {

    private final RhService rhService;

    public RhController(RhService rhService) {
        this.rhService = rhService;
    }

    /**
     * Création d'un RH (ADMIN seulement)
     */
    /**
     * Création d'un RH (ADMIN seulement)
     */
    @PostMapping("/register")
    public ResponseEntity<RhDto> create(@RequestBody Rh rh) {
        Rh created = rhService.create(rh);
        return new ResponseEntity<>(toDto(created), HttpStatus.CREATED);
    }

    /**
     * Liste tous les RH (CONFIGURATEUR/ADMIN)
     */
    @GetMapping
    public ResponseEntity<List<RhDto>> list() {
        List<RhDto> dtos = rhService.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Récupère un RH par ID (CONFIGURATEUR/ADMIN)
     */
    @GetMapping("/{id}")
    public ResponseEntity<RhDto> get(@PathVariable String id) {
        Rh rh = rhService.findById(id);
        return ResponseEntity.ok(toDto(rh));
    }

    /**
     * Mise à jour d'un RH (CONFIGURATEUR/ADMIN)
     */
    @PutMapping("/{id}")
    public ResponseEntity<RhDto> update(@PathVariable String id, @RequestBody Rh rh) {
        Rh updated = rhService.update(id, rh);
        return ResponseEntity.ok(toDto(updated));
    }

    /**
     * Suppression d'un RH (ADMIN seulement)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        rhService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Mapping de l'entité Rh vers un DTO pour sérialisation sécurisée
     */
    private RhDto toDto(Rh rh) {
        RhDto dto = new RhDto();
        dto.setIdRh(rh.getIdRh());
        dto.setNom(rh.getNom());
        dto.setPrenom(rh.getPrenom());
        dto.setEmail(rh.getEmail());
        dto.setDateCreation(rh.getDateCreation());
        dto.setRole(rh.getRole().getNomRole());
        return dto;
    }

    /**
     * DTO interne pour éviter la LazyInitializationException
     */
    public static class RhDto {
        private String idRh;
        private String nom;
        private String prenom;
        private String email;
        private java.sql.Date dateCreation;
        private String role;

        public String getIdRh() { return idRh; }
        public void setIdRh(String idRh) { this.idRh = idRh; }

        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }

        public String getPrenom() { return prenom; }
        public void setPrenom(String prenom) { this.prenom = prenom; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public java.sql.Date getDateCreation() { return dateCreation; }
        public void setDateCreation(java.sql.Date dateCreation) { this.dateCreation = dateCreation; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
}
