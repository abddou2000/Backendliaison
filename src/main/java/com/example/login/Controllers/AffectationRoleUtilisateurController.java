package com.example.login.Controllers;

import com.example.login.Models.AffectationRoleUtilisateur;
import com.example.login.Models.Role;
import com.example.login.Models.Utilisateur;
import com.example.login.Repositories.AffectationRoleUtilisateurRepository;
import com.example.login.Repositories.RoleRepository;
import com.example.login.Repositories.UtilisateurRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/affectations-roles")
public class AffectationRoleUtilisateurController {

    private final AffectationRoleUtilisateurRepository aruRepo;
    private final UtilisateurRepository userRepo;
    private final RoleRepository roleRepo;

    public AffectationRoleUtilisateurController(
            AffectationRoleUtilisateurRepository aruRepo,
            UtilisateurRepository userRepo,
            RoleRepository roleRepo
    ) {
        this.aruRepo = aruRepo;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    // === 1) Lister les rôles d’un utilisateur ===
    @GetMapping("/utilisateurs/{userId}/roles")
    public ResponseEntity<List<RoleDTO>> listRolesOfUser(@PathVariable Long userId) {
        // 404 si l'utilisateur n'existe pas (sécurité)
        userRepo.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        var affects = aruRepo.findByUtilisateur_Id(userId);
        var dto = affects.stream()
                .map(a -> new RoleDTO(a.getRole().getId(), a.getRole().getType()))
                .toList();
        return ResponseEntity.ok(dto);
    }

    // === 2) Lister les utilisateurs possédant un rôle ===
    @GetMapping("/roles/{roleId}/utilisateurs")
    public ResponseEntity<List<UserDTO>> listUsersByRole(@PathVariable Long roleId) {
        // 404 si le rôle n'existe pas (sécurité)
        roleRepo.findById(roleId).orElseThrow(() -> new RuntimeException("Rôle introuvable"));
        var affects = aruRepo.findByRole_Id(roleId);
        var dto = affects.stream()
                .map(a -> new UserDTO(a.getUtilisateur().getId(), a.getUtilisateur().getUsername()))
                .toList();
        return ResponseEntity.ok(dto);
    }

    // === 3) Vérifier si un user a un rôle (utile pour le switch) ===
    @GetMapping("/utilisateurs/{userId}/roles/{roleId}/exists")
    public ResponseEntity<Boolean> hasRole(@PathVariable Long userId, @PathVariable Long roleId) {
        boolean exists = aruRepo.existsByUtilisateur_IdAndRole_Id(userId, roleId);
        return ResponseEntity.ok(exists);
    }

    // === 4) Ajouter un rôle à un utilisateur (idempotent) ===
    @PostMapping("/utilisateurs/{userId}/roles/{roleId}")
    @Transactional
    public ResponseEntity<Void> addRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        Utilisateur user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        Role role = roleRepo.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rôle introuvable"));

        boolean already = aruRepo.existsByUtilisateur_IdAndRole_Id(userId, roleId);
        if (already) return ResponseEntity.noContent().build(); // idempotent

        var id = new AffectationRoleUtilisateur.Id(userId, roleId);
        var affect = new AffectationRoleUtilisateur(id, user, role);
        aruRepo.save(affect);
        return ResponseEntity.noContent().build();
    }

    // === 5) Retirer un rôle d’un utilisateur (idempotent) ===
    @DeleteMapping("/utilisateurs/{userId}/roles/{roleId}")
    @Transactional
    public ResponseEntity<Void> removeRoleFromUser(@PathVariable Long userId, @PathVariable Long roleId) {
        boolean exists = aruRepo.existsByUtilisateur_IdAndRole_Id(userId, roleId);
        if (!exists) return ResponseEntity.noContent().build(); // idempotent
        aruRepo.deleteByUserAndRole(userId, roleId);
        return ResponseEntity.noContent().build();
    }

    // DTOs simples et stables (évite les boucles JSON)
    @Data
    static class RoleDTO {
        private final Long id;
        private final String type;
    }

    @Data
    static class UserDTO {
        private final Long id;
        private final String username;
    }
}
