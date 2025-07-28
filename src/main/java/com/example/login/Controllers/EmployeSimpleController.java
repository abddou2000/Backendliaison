package com.example.login.Controllers;

import com.example.login.Models.EmployeSimple;
import com.example.login.Repositories.EmployeSimpleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employes")
@CrossOrigin(origins = "*")
public class EmployeSimpleController {

    @Autowired
    private EmployeSimpleRepository repo;

    /** Créer un employé **/
    @PostMapping
    public ResponseEntity<EmployeSimple> create(@RequestBody EmployeSimple emp) {
        EmployeSimple saved = repo.save(emp);
        return ResponseEntity.ok(saved);
    }

    /** Lister tous les employés **/
    @GetMapping
    public ResponseEntity<List<EmployeSimple>> list() {
        return ResponseEntity.ok(repo.findAll());
    }

    /** Récupérer un employé par son ID **/
    @GetMapping("/{id}")
    public ResponseEntity<EmployeSimple> getById(@PathVariable String id) {
        Optional<EmployeSimple> opt = repo.findById(id);
        return opt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Mettre à jour un employé existant **/
    @PutMapping("/{id}")
    public ResponseEntity<EmployeSimple> update(
            @PathVariable String id,
            @RequestBody EmployeSimple details
    ) {
        return repo.findById(id)
                .map(emp -> {
                    // Ne mettez à jour que les champs souhaités
                    emp.setNom(details.getNom());
                    emp.setPrenom(details.getPrenom());
                    emp.setEmail(details.getEmail());
                    emp.setEmailPro(details.getEmailPro());
                    emp.setEmailPerso(details.getEmailPerso());
                    emp.setMotDePasse(details.getMotDePasse());
                    emp.setDateNaissance(details.getDateNaissance());
                    emp.setTelephone(details.getTelephone());
                    emp.setAdresse(details.getAdresse());
                    emp.setCin(details.getCin());
                    emp.setDateEmbauche(details.getDateEmbauche());
                    emp.setDateCreation(details.getDateCreation());
                    emp.setNomUtilisateur(details.getNomUtilisateur());
                    emp.setUniteOrganisationnelle(details.getUniteOrganisationnelle());
                    emp.setSociete(details.getSociete());
                    emp.setRole(details.getRole());
                    return ResponseEntity.ok(repo.save(emp));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /** Supprimer un employé **/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
