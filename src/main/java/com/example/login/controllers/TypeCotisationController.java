package com.example.login.controllers;

import com.example.login.models.TypeCotisation;
import com.example.login.services.TypeCotisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/type-cotisations")
@CrossOrigin(origins = "*")
public class TypeCotisationController {

    private final TypeCotisationService service;

    @Autowired
    public TypeCotisationController(TypeCotisationService service) {
        this.service = service;
    }

    // Créer un nouveau type de cotisation
    @PostMapping
    public TypeCotisation create(@RequestBody TypeCotisation typeCotisation) {
        return service.save(typeCotisation);
    }

    // Obtenir tous les types
    @GetMapping
    public List<TypeCotisation> getAll() {
        return service.listAll();
    }

    // Obtenir par ID
    @GetMapping("/{id}")
    public TypeCotisation getById(@PathVariable String id) {
        return service.getById(id);
    }

    // Mettre à jour
    @PutMapping("/{id}")
    public TypeCotisation update(@PathVariable String id, @RequestBody TypeCotisation updated) {
        return service.update(id, updated);
    }

    // Supprimer
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable String id) {
        return service.delete(id);
    }

    // Chercher par nom
    @GetMapping("/nom/{nom}")
    public List<TypeCotisation> findByNom(@PathVariable String nom) {
        return service.findByName(nom);
    }

    // Chercher par code
    @GetMapping("/code/{code}")
    public List<TypeCotisation> findByCode(@PathVariable String code) {
        return service.findByCode(code);
    }

    // Chercher entre deux dates
    @GetMapping("/periode")
    public List<TypeCotisation> findByPeriode(
            @RequestParam("start") Date start,
            @RequestParam("end") Date end) {
        return service.findByPeriod(start, end);
    }

    // Commencée après une date
    @GetMapping("/apres")
    public List<TypeCotisation> findAfter(@RequestParam("date") Date date) {
        return service.findStartedAfter(date);
    }

    // Fin avant une date
    @GetMapping("/avant")
    public List<TypeCotisation> findBefore(@RequestParam("date") Date date) {
        return service.findEndingBefore(date);
    }
}
