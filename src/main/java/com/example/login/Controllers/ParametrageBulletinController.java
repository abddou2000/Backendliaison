package com.example.login.Controllers;

import com.example.login.Models.ParametrageBulletin;
import com.example.login.Services.ParametrageBulletinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parametrages-bulletin")
@CrossOrigin(origins = "*")
public class ParametrageBulletinController {

    @Autowired
    private ParametrageBulletinService service;

    @PostMapping public ResponseEntity<ParametrageBulletin> create(@RequestBody ParametrageBulletin pb){
        return ResponseEntity.ok(service.create(pb));
    }
    @GetMapping public ResponseEntity<List<ParametrageBulletin>> list(){
        return ResponseEntity.ok(service.getAll());
    }
    @GetMapping("/{id}") public ResponseEntity<ParametrageBulletin> get(@PathVariable String id){
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}") public ResponseEntity<ParametrageBulletin> update(@PathVariable String id,@RequestBody ParametrageBulletin pb){
        if(!service.existsById(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(service.update(id,pb));
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable String id){
        if(!service.existsById(id)) return ResponseEntity.notFound().build();
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
