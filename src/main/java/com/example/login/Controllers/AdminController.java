// src/main/java/com/example/login/Controllers/AdminController.java
package com.example.login.Controllers;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
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

    @Operation(summary = "Add a new configurateur", description = "Creates a new configurateur with the provided details.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Configurateur created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/add-configurateur")
    public ResponseEntity<?> addConfigurateur(@RequestBody UserRegistrationDto registrationDto) {
        try {
            Configurateur configurateur = new Configurateur();
            configurateur.setNom(registrationDto.getEmployeSimple().getNom());
            configurateur.setPrenom(registrationDto.getEmployeSimple().getPrenom());
            configurateur.setEmail(registrationDto.getEmployeSimple().getEmailPro());

            Configurateur saved = configurateurService.createConfigurateur(configurateur, registrationDto.getEmployeSimple());
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating configurateur: " + e.getMessage());
        }
    }

    @Operation(summary = "Add a new RH", description = "Creates a new RH with the provided details.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "RH created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/add-rh")
    public ResponseEntity<?> addRh(@RequestBody UserRegistrationDto registrationDto) {
        try {
            Rh rh = new Rh();
            rh.setNom(registrationDto.getEmployeSimple().getNom());
            rh.setPrenom(registrationDto.getEmployeSimple().getPrenom());
            rh.setEmail(registrationDto.getEmployeSimple().getEmailPro());

            Rh saved = rhService.createRh(rh, registrationDto.getEmployeSimple());
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating RH: " + e.getMessage());
        }
    }

    @Operation(summary = "Add a new simple employee", description = "Registers a new simple employee.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Employee created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/add-simple-employee")
    public ResponseEntity<?> addSimpleEmployee(@RequestBody EmployeSimple employeSimple) {
        try {
            authService.registerUser(employeSimple);
            return ResponseEntity.ok("Employee created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating employee: " + e.getMessage());
        }
    }
}
