package com.example.login.Controllers;

import com.example.login.Models.Administrateur;
import com.example.login.Services.AdministrateurService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administrateurs")
@CrossOrigin(origins = "*") // N'oubliez pas d'importer @CrossOrigin
public class AdministrateurController {

    private final AdministrateurService service;

    public AdministrateurController(AdministrateurService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Administrateur> create(@Valid @RequestBody Administrateur adminData) {
        Administrateur createdAdmin = service.create(adminData);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdmin);
    }

    @GetMapping
    public List<Administrateur> listAll() {
        return service.getAllAdministrateurs();
    }

    @GetMapping("/{id}")
    public Administrateur getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}