package com.example.login.Controllers;

import com.example.login.Controllers.dto.CreateConfigurateurDto;
import com.example.login.Models.Configurateur;
import com.example.login.Models.EmployeSimple;
import com.example.login.Models.Rh;
import com.example.login.Services.AuthenticationService;
import com.example.login.Services.ConfigurateurService;
import com.example.login.Services.RhService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")  // accessible uniquement aux ADMIN
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
     * - specificData pourra être utilisé plus tard si besoin
     */
    public static class UserRegistrationDto {
        private EmployeSimple employeSimple;
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

    // 1) Création d’un Configurateur
    @PostMapping("/add-configurateur")
    public ResponseEntity<?> addConfigurateur(
            @Valid @RequestBody CreateConfigurateurDto dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            bindingResult.getFieldErrors().forEach(fe ->
                    sb.append(fe.getField())
                            .append(": ")
                            .append(fe.getDefaultMessage())
                            .append("; ")
            );
            return ResponseEntity.badRequest().body(sb.toString());
        }

        try {
            Configurateur saved = configurateurService.createFromDto(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating configurateur: " + e.getMessage());
        }
    }

    // 2) Création d’un RH
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

    // 3) Création d’un Employé Simple
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
