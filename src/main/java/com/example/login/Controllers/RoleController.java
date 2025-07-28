package com.example.login.Controllers;

import com.example.login.Models.Role;
import com.example.login.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RoleController {

    @Autowired
    private RoleService service;

    /** Créer ou mettre à jour un rôle */
    @PostMapping
    public ResponseEntity<Role> createOrUpdateRole(@RequestBody Role role) {
        Role saved = service.saveRole(role);
        return ResponseEntity.ok(saved);
    }

    /** Lister tous les rôles */
    @GetMapping
    public ResponseEntity<List<Role>> listRoles() {
        return ResponseEntity.ok(service.getAllRoles());
    }

    /** Récupérer un rôle par ID */
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRole(@PathVariable("id") String id) {
        return service.getRoleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Supprimer un rôle */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") String id) {
        service.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
