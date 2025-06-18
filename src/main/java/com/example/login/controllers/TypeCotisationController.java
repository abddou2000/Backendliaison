package com.example.login.controllers;

import com.example.login.models.TypeCotisation;
import com.example.login.Services.TypeCotisationService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/type-cotisations")
@CrossOrigin(origins = "*")
public class TypeCotisationController {

    private final TypeCotisationService service;
    private final ObjectMapper mapper;

    @Autowired
    public TypeCotisationController(TypeCotisationService service) {
        this.service = service;
        this.mapper = new ObjectMapper()
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // ← plus de @RequestBody : on lit brutement l'InputStream
    @PostMapping
    public ResponseEntity<TypeCotisation> create(HttpServletRequest request) throws IOException {
        TypeCotisation t = mapper.readValue(request.getInputStream(), TypeCotisation.class);
        TypeCotisation saved = service.save(t);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<TypeCotisation>> getAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeCotisation> getById(@PathVariable String id) {
        TypeCotisation t = service.getById(id);
        return t != null
                ? ResponseEntity.ok(t)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeCotisation> update(
            @PathVariable String id,
            @RequestBody TypeCotisation updated
    ) {
        TypeCotisation t = service.update(id, updated);
        return t != null
                ? ResponseEntity.ok(t)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<List<TypeCotisation>> findByNom(@PathVariable String nom) {
        return ResponseEntity.ok(service.findByName(nom));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<List<TypeCotisation>> findByCode(@PathVariable String code) {
        return ResponseEntity.ok(service.findByCode(code));
    }

    @GetMapping("/periode")
    public ResponseEntity<List<TypeCotisation>> findByPeriode(
            @RequestParam("start") Date start,
            @RequestParam("end")   Date end
    ) {
        return ResponseEntity.ok(service.findByPeriod(start, end));
    }

    @GetMapping("/apres")
    public ResponseEntity<List<TypeCotisation>> findAfter(@RequestParam("date") Date date) {
        return ResponseEntity.ok(service.findStartedAfter(date));
    }

    @GetMapping("/avant")
    public ResponseEntity<List<TypeCotisation>> findBefore(@RequestParam("date") Date date) {
        return ResponseEntity.ok(service.findEndingBefore(date));
    }
}
