package com.example.login.controllers;

import com.example.login.models.TypeCotisation;
import com.example.login.services.TypeCotisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    // — Créer un nouveau type de cotisation
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public TypeCotisation create(@RequestBody TypeCotisation typeCotisation) {
        return service.save(typeCotisation);
    }

    // — Obtenir tous les types
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TypeCotisation> getAll() {
        return service.listAll();
    }

    // — Obtenir par ID
    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public TypeCotisation getById(@PathVariable String id) {
        return service.getById(id);
    }

    // — Mettre à jour
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public TypeCotisation update(
            @PathVariable String id,
            @RequestBody TypeCotisation updated
    ) {
        return service.update(id, updated);
    }

    // — Supprimer
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable String id) {
        return service.delete(id);
    }

    // — Recherches diverses (toutes en JSON)
    @GetMapping(value = "/nom/{nom}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TypeCotisation> findByNom(@PathVariable String nom) {
        return service.findByName(nom);
    }

    @GetMapping(value = "/code/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TypeCotisation> findByCode(@PathVariable String code) {
        return service.findByCode(code);
    }

    @GetMapping(value = "/periode", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TypeCotisation> findByPeriode(
            @RequestParam("start") Date start,
            @RequestParam("end")   Date end
    ) {
        return service.findByPeriod(start, end);
    }

    @GetMapping(value = "/apres", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TypeCotisation> findAfter(@RequestParam("date") Date date) {
        return service.findStartedAfter(date);
    }

    @GetMapping(value = "/avant", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TypeCotisation> findBefore(@RequestParam("date") Date date) {
        return service.findEndingBefore(date);
    }
}
