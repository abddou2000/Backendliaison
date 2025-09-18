    package com.example.login.Controllers;
    
    import com.example.login.Models.FichePaie;
    import com.example.login.Services.FichePaieService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    
    import java.util.List;
    
    @RestController
    @RequestMapping("/api/fiches-paie")
    @CrossOrigin(origins = "*")
    public class FichePaieController {
    
        @Autowired
        private FichePaieService service;
    
        // CREATE
        @PostMapping
        public ResponseEntity<FichePaie> create(@RequestBody FichePaie fiche) {
            return ResponseEntity.ok(service.create(fiche));
        }
    
        // READ ALL
        @GetMapping
        public ResponseEntity<List<FichePaie>> getAll() {
            return ResponseEntity.ok(service.getAll());
        }
    
        // READ BY ID
        @GetMapping("/{id}")
        public ResponseEntity<FichePaie> getById(@PathVariable String id) {
            return service.getById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
    
        // CORRECTED METHOD: Changed @PathVariable to Long
        // READ BY EMPLOYE
        @GetMapping("/employe/{idEmploye}")
        public ResponseEntity<List<FichePaie>> getByEmploye(@PathVariable Long idEmploye) {
            return ResponseEntity.ok(service.getByEmploye(idEmploye));
        }
    
        // READ BY PERIODE
        @GetMapping("/periode/{idPeriode}")
        public ResponseEntity<List<FichePaie>> getByPeriode(@PathVariable String idPeriode) {
            return ResponseEntity.ok(service.getByPeriode(idPeriode));
        }
    
        // READ BY ANNEE & MOIS
        @GetMapping("/periode/{annee}/{mois}")
        public ResponseEntity<List<FichePaie>> getByAnneeAndMois(@PathVariable Integer annee, @PathVariable Integer mois) {
            return ResponseEntity.ok(service.getByAnneeAndMois(annee, mois));
        }
    
        // UPDATE
        @PutMapping("/{id}")
        public ResponseEntity<FichePaie> update(@PathVariable String id, @RequestBody FichePaie updated) {
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