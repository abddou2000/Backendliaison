package com.example.login.Services;

import com.example.login.Models.AffectationRoleUtilisateur;
import com.example.login.Models.Role;
import com.example.login.Models.Utilisateur;
import com.example.login.Repositories.AffectationRoleUtilisateurRepository;
import com.example.login.Repositories.RoleRepository;
import com.example.login.Repositories.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AffectationRoleUtilisateurService {

    private final AffectationRoleUtilisateurRepository aruRepo;
    private final UtilisateurRepository userRepo;
    private final RoleRepository roleRepo;

    public AffectationRoleUtilisateurService(
            AffectationRoleUtilisateurRepository aruRepo,
            UtilisateurRepository userRepo,
            RoleRepository roleRepo
    ) {
        this.aruRepo = aruRepo;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    // ---------- Lectures ----------

    /** Tous les rôles (entités Role) d’un utilisateur */
    public List<Role> getRolesOfUser(Long userId) {
        ensureUserExists(userId);
        return aruRepo.findByUtilisateur_Id(userId)
                .stream()
                .map(AffectationRoleUtilisateur::getRole)
                .toList();
    }

    /** IDs des rôles d’un utilisateur (utile pour auth/tests) */
    public List<Long> getRoleIdsOfUser(Long userId) {
        ensureUserExists(userId);
        return aruRepo.findByUtilisateur_Id(userId)
                .stream()
                .map(a -> a.getRole().getId())
                .toList();
    }

    /** Libellés/types des rôles d’un utilisateur (ex: CONFIGURATEUR, EMPLOYE) */
    public List<String> getRoleTypesOfUser(Long userId) {
        ensureUserExists(userId);
        return aruRepo.findByUtilisateur_Id(userId)
                .stream()
                .map(a -> a.getRole().getType())
                .toList();
    }

    /** Tous les utilisateurs (entités) qui possèdent un rôle donné (par id) */
    public List<Utilisateur> getUsersByRoleId(Long roleId) {
        ensureRoleExists(roleId);
        return aruRepo.findByRole_Id(roleId)
                .stream()
                .map(AffectationRoleUtilisateur::getUtilisateur)
                .toList();
    }

    /** Vérifie si un utilisateur possède un rôle (par id) */
    public boolean hasRole(Long userId, Long roleId) {
        return aruRepo.existsByUtilisateur_IdAndRole_Id(userId, roleId);
    }

    // ---------- Écritures (idempotentes) ----------

    /** Ajoute un rôle à un utilisateur (idempotent) */
    @Transactional
    public void addRoleToUser(Long userId, Long roleId) {
        Utilisateur user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable"));
        Role role = roleRepo.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Rôle introuvable"));

        if (aruRepo.existsByUtilisateur_IdAndRole_Id(userId, roleId)) {
            return; // idempotent
        }
        var id = new AffectationRoleUtilisateur.Id(userId, roleId);
        var affect = new AffectationRoleUtilisateur(id, user, role);
        aruRepo.save(affect);
    }

    /** Retire un rôle d’un utilisateur (idempotent) */
    @Transactional
    public void removeRoleFromUser(Long userId, Long roleId) {
        if (!aruRepo.existsByUtilisateur_IdAndRole_Id(userId, roleId)) {
            return; // idempotent
        }
        aruRepo.deleteByUserAndRole(userId, roleId);
    }

    // ---------- Utilitaires ----------

    private void ensureUserExists(Long userId) {
        if (!userRepo.existsById(userId)) {
            throw new EntityNotFoundException("Utilisateur introuvable");
        }
    }

    private void ensureRoleExists(Long roleId) {
        if (!roleRepo.existsById(roleId)) {
            throw new EntityNotFoundException("Rôle introuvable");
        }
    }
}
