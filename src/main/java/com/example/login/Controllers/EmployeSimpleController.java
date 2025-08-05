package com.example.login.Controllers;

import com.example.login.Models.EmployeSimple;
import com.example.login.Services.EmployeSimpleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employes")
@CrossOrigin(origins = "*") // N'oubliez pas d'ajouter cette ligne si nécessaire
public class EmployeSimpleController {

    private final EmployeSimpleService service;

    public EmployeSimpleController(EmployeSimpleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EmployeSimple> create(@Valid @RequestBody EmployeSimple emp) {
        // On n'a plus besoin du @RequestParam roleType
        EmployeSimple createdEmploye = service.create(emp);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmploye);
    }

    @GetMapping
    public List<EmployeSimple> listAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeSimple> getById(@PathVariable Long id) {
        Optional<EmployeSimple> employe = service.getById(id);
        return employe.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}