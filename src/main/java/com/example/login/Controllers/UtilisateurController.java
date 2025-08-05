package com.example.login.Controllers;

import com.example.login.Models.Utilisateur;
import com.example.login.Services.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {
    private final UtilisateurService service;

    public UtilisateurController(UtilisateurService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Utilisateur create(@Valid @RequestBody Utilisateur user,
                              @RequestParam String roleType) {
        return service.create(user, roleType);
    }

    @GetMapping
    public List<Utilisateur> listAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Utilisateur getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable Long id,
                               @RequestParam String newPassword) {
        service.updatePassword(id, newPassword);
    }
}
