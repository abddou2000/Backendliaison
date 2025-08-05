package com.example.login.Controllers;

import com.example.login.Models.Configurateur;
import com.example.login.Services.ConfigurateurService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configurateurs")
public class ConfigurateurController {

    private final ConfigurateurService service;

    public ConfigurateurController(ConfigurateurService service) {
        this.service = service;
    }

    @GetMapping
    public List<Configurateur> listAll() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public Configurateur getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Configurateur create(@RequestBody Configurateur configurateur) {
        return service.create(configurateur);
    }

    @PutMapping("/{id}")
    public Configurateur update(@PathVariable Long id, @RequestBody Configurateur configurateur) {
        configurateur.setId(id);
        return service.create(configurateur); // assuming create upserts; or implement update in service
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id); // implement delete(Long) in your ConfigurateurService if needed
    }
}
