package com.example.login.Controllers;

import com.example.login.Models.RubriqueBulletin;
import com.example.login.Services.RubriqueBulletinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rubriques-bulletin")
@CrossOrigin(origins = "*")
public class RubriqueBulletinController {

    @Autowired
    private RubriqueBulletinService service;

    @PostMapping public ResponseEntity<RubriqueBulletin> create(@RequestBody RubriqueBulletin rb) {
        return ResponseEntity.ok(service.create(rb));
    }
    @GetMapping public ResponseEntity<List<RubriqueBulletin>> list() {
        return ResponseEntity.ok(service.getAll());
    }
    @GetMapping("/{id}") public ResponseEntity<RubriqueBulletin> get(@PathVariable String id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}") public ResponseEntity<RubriqueBulletin> update(@PathVariable String id,@RequestBody RubriqueBulletin rb){
        if(!service.existsById(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(service.update(id, rb));
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable String id){
        if(!service.existsById(id)) return ResponseEntity.notFound().build();
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
