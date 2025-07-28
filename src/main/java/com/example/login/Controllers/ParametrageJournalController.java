package com.example.login.Controllers;

import com.example.login.Models.ParametrageJournal;
import com.example.login.Services.ParametrageJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parametrages-journal")
@CrossOrigin(origins = "*")
public class ParametrageJournalController {

    @Autowired
    private ParametrageJournalService service;

    @PostMapping public ResponseEntity<ParametrageJournal> create(@RequestBody ParametrageJournal pj){
        return ResponseEntity.ok(service.create(pj));
    }
    @GetMapping public ResponseEntity<List<ParametrageJournal>> list(){
        return ResponseEntity.ok(service.getAll());
    }
    @GetMapping("/{id}") public ResponseEntity<ParametrageJournal> get(@PathVariable String id){
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}") public ResponseEntity<ParametrageJournal> update(@PathVariable String id,@RequestBody ParametrageJournal pj){
        if(!service.existsById(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(service.update(id,pj));
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable String id){
        if(!service.existsById(id)) return ResponseEntity.notFound().build();
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
