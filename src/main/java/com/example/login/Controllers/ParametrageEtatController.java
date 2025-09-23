package com.example.login.Controllers;

import com.example.login.Models.ParametrageEtat;
import com.example.login.Services.ParametrageEtatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parametrages-etat")
@CrossOrigin(origins = "*")
public class ParametrageEtatController {

    private final ParametrageEtatService service;

    public ParametrageEtatController(ParametrageEtatService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParametrageEtat create(@RequestBody ParametrageEtat body) {
        return service.create(body);
    }

    // READ ALL avec filtres optionnels ?nomFormulaire=BP&visibilite=true
    @GetMapping
    public ResponseEntity<List<ParametrageEtat>> getAll(
            @RequestParam(required = false) String nomFormulaire,
            @RequestParam(required = false) Boolean visibilite
    ) {
        return ResponseEntity.ok(service.search(nomFormulaire, visibilite));
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ParametrageEtat> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ParametrageEtat> update(@PathVariable Long id,
                                                  @RequestBody ParametrageEtat body) {
        return ResponseEntity.ok(service.update(id, body));
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
