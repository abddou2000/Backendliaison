// src/main/java/com/example/login/Controllers/ConfigurateurController.java
package com.example.login.Controllers;

import com.example.login.Controllers.dto.CreateConfigurateurDto;
import com.example.login.Models.Configurateur;
import com.example.login.Services.ConfigurateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configurateurs")
public class ConfigurateurController {

    private final ConfigurateurService configurateurService;

    @Autowired
    public ConfigurateurController(ConfigurateurService configurateurService) {
        this.configurateurService = configurateurService;
    }

    /**
     * Crée un nouveau configurateur.
     * Accessible uniquement aux utilisateurs ayant le rôle ADMIN.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createConfigurateur(
            @Valid @RequestBody CreateConfigurateurDto dto,
            BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            StringBuilder erreurs = new StringBuilder();
            bindingResult.getFieldErrors().forEach(fe -> {
                erreurs.append(fe.getField())
                        .append(": ")
                        .append(fe.getDefaultMessage())
                        .append("; ");
            });
            return ResponseEntity.badRequest().body(erreurs.toString());
        }

        try {
            Configurateur saved = configurateurService.createFromDto(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Impossible de créer le configurateur : " + e.getMessage());
        }
    }

    /**
     * Récupère la liste de tous les configurateurs.
     * Accessible uniquement aux utilisateurs ayant le rôle ADMIN.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Configurateur>> getAllConfigurateurs() {
        List<Configurateur> list = configurateurService.listConfigurateurs();
        return ResponseEntity.ok(list);
    }

    /**
     * Récupère un configurateur par son ID.
     * Accessible uniquement aux utilisateurs ayant le rôle ADMIN.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Configurateur> getConfigurateurById(@PathVariable String id) {
        Configurateur cfg = configurateurService.getConfigurateurById(id);
        if (cfg == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cfg);
    }

    // ... autres endpoints (update, delete) …
}
