// src/main/java/com/example/login/Controllers/AdminController.java
package com.example.login.Controllers;

import com.example.login.Controllers.dto.CreateConfigurateurDto;
import com.example.login.Models.Configurateur;
import com.example.login.Models.EmployeSimple;
import com.example.login.Models.Rh;
import com.example.login.Services.AuthenticationService;
import com.example.login.Services.ConfigurateurService;
import com.example.login.Services.RhService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')") // ● Toutes les méthodes de ce contrôleur sont réservées aux utilisateurs avec ROLE_ADMIN
public class AdminController {

    private final ConfigurateurService configurateurService;
    private final RhService rhService;
    private final AuthenticationService authService;

    @Autowired
    public AdminController(
            ConfigurateurService configurateurService,
            RhService rhService,
            AuthenticationService authService) {
        this.configurateurService = configurateurService;
        this.rhService = rhService;
        this.authService = authService;
    }

    /**
     * DTO interne pour la création d’un RH :
     * - contient un EmployeSimple (nom, prénom, emailPro, etc.)
     * - specificData est masqué dans la documentation Swagger
     */
    public static class UserRegistrationDto {
        private EmployeSimple employeSimple;
        @Schema(hidden = true)
        private Object specificData;

        public EmployeSimple getEmployeSimple() {
            return employeSimple;
        }

        public void setEmployeSimple(EmployeSimple employeSimple) {
            this.employeSimple = employeSimple;
        }

        public Object getSpecificData() {
            return specificData;
        }

        public void setSpecificData(Object specificData) {
            this.specificData = specificData;
        }
    }

    // -------------------------------------------------------
    // 1) Endpoint : Création d’un Configurateur (ADMIN uniquement)
    // -------------------------------------------------------
    @Operation(
            summary = "Add a new configurateur",
            description = "Permet à un ADMIN de créer un nouveau Configurateur en fournissant un JSON plat."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Configurateur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping("/add-configurateur")
    public ResponseEntity<?> addConfigurateur(
            @Valid @RequestBody CreateConfigurateurDto dto, // ● DTO “plat” validé automatiquement
            BindingResult bindingResult)                  // ● Résultat de la validation
    {
        // 1) Vérification des champs invalides
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            bindingResult.getFieldErrors().forEach(fe -> {
                sb.append(fe.getField())
                        .append(": ")
                        .append(fe.getDefaultMessage())
                        .append("; ");
            });
            return ResponseEntity.badRequest().body(sb.toString());
        }

        try {
            // 2) Appel au service pour créer EmployeSimple + Configurateur
            Configurateur saved = configurateurService.createFromDto(dto);
            // 3) Retourne 201 Created avec l’objet créé
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            // 4) En cas d’erreur (par ex. rôle manquant, exception JPA, etc.)
            return ResponseEntity.badRequest().body("Error creating configurateur: " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // 2) Endpoint : Création d’un RH (ADMIN uniquement)
    // -------------------------------------------------------
    @Operation(
            summary = "Add a new RH",
            description = "Permet à un ADMIN de créer un nouveau RH en fournissant un EmployeSimple imbriqué."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "RH créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping("/add-rh")
    public ResponseEntity<?> addRh(@RequestBody UserRegistrationDto registrationDto) {
        try {
            // 1) Construction de l’entité Rh à partir des données de EmployeSimple
            Rh rh = new Rh();
            rh.setNom(registrationDto.getEmployeSimple().getNom());
            rh.setPrenom(registrationDto.getEmployeSimple().getPrenom());
            rh.setEmail(registrationDto.getEmployeSimple().getEmailPro());
            // (Vous pouvez mapper d’autres champs selon la définition de Rh)

            // 2) Appel au service pour persister l’employé RH + l’entité RH
            Rh saved = rhService.createRh(rh, registrationDto.getEmployeSimple());
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            // 3) En cas d’erreur lors de la création
            return ResponseEntity.badRequest().body("Error creating RH: " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // 3) Endpoint : Création d’un Employé Simple (ADMIN uniquement)
    // -------------------------------------------------------
    @Operation(
            summary = "Add a new simple employee",
            description = "Permet à un ADMIN de créer un Employé Simple."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employé créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping("/add-simple-employee")
    public ResponseEntity<?> addSimpleEmployee(@RequestBody EmployeSimple employeSimple) {
        try {
            // Appel au service d’authentification pour enregistrer l’employé
            authService.registerUser(employeSimple);
            return ResponseEntity.ok("Employee created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating employee: " + e.getMessage());
        }
    }
}
