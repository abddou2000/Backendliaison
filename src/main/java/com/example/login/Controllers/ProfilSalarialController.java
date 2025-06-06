package com.example.login.Controllers;

import com.example.login.Models.ProfilSalarial;
import com.example.login.Services.ProfilSalarialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profils-salariaux")
@CrossOrigin(origins = "*")
public class ProfilSalarialController {

    @Autowired
    private ProfilSalarialService service;

    // CREATE
    @PostMapping
    public ResponseEntity<ProfilSalarial> create(@RequestBody ProfilSalarial profil) {
        return ResponseEntity.ok(service.create(profil));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<ProfilSalarial>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ProfilSalarial> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // READ BY CATEGORIE
    @GetMapping("/categorie/{idCategorie}")
    public ResponseEntity<List<ProfilSalarial>> getByCategorie(@PathVariable String idCategorie) {
        return ResponseEntity.ok(service.getByCategorie(idCategorie));
    }

    // READ BY STATUT
    @GetMapping("/statut/{idStatut}")
    public ResponseEntity<List<ProfilSalarial>> getByStatut(@PathVariable String idStatut) {
        return ResponseEntity.ok(service.getByStatut(idStatut));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ProfilSalarial> update(@PathVariable String id, @RequestBody ProfilSalarial updated) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.update(id, updated));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
